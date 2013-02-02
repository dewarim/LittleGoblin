package de.dewarim.goblin

import de.dewarim.goblin.combat.Melee
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.combat.MeleeFighter
import de.dewarim.goblin.combat.MeleeAction
import de.dewarim.goblin.combat.CombatMessage
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.ItemTypeFeature
import de.dewarim.goblin.ticks.ITickListener

class MeleeService implements ITickListener{

    def globalConfigService
    def playerMessageService
    def itemService
    def featureService

    def tr

    Melee createMelee() {
        Melee m = new Melee()
        m.save()
        return m
    }

    List<PlayerCharacter> listFighters(Melee melee) {
        return melee.fighters.findAll { meleeFighter ->
            !(meleeFighter.state.equals(FighterState.QUITTER) || meleeFighter.state.equals(FighterState.DEAD))
        }.collect {it.pc}.sort {it.xp}
    }

    Melee findOrCreateMelee() {
        Melee melee = Melee.findByStatus(MeleeStatus.RUNNING)
        if (!melee) {
            melee = Melee.findByStatus(MeleeStatus.WAITING)
            if (!melee) {
                // the MeleeController should have created a waiting melee round, but this
                // guarantees that we will have in any case:
                melee = createMelee()
            }
        }
        return melee
    }

    MeleeFighter joinMelee(PlayerCharacter pc, Melee melee) {
        MeleeFighter mf = new MeleeFighter(melee, pc)
        mf.save()
    }

    void leaveMelee(PlayerCharacter pc, Melee melee) {
        MeleeFighter mf = MeleeFighter.findByPcAndMelee(pc, melee)
        pc.currentMelee = null
        mf.state = FighterState.QUITTER
    }

    /**
     * Check if a player character is already on the team or has quit previously (by leaving or dying).
     */
    Boolean newFighterAllowed(PlayerCharacter pc, Melee melee) {
        MeleeFighter mf = MeleeFighter.findByPcAndMelee(pc, melee)
        return mf == null
    }

    void fightMelee(Melee melee) {
        Map<PlayerCharacter, MeleeFighter> pcFighters = [:]
        melee.fighters.findAll {it.state.equals(FighterState.ACTIVE)}.each {
            pcFighters.put(it.pc, it)
        }

        // 1. roll initiative
        List<MeleeAction> actions = rollInitiative(pcFighters)

        // 2. execute actions
        actions.each {action ->
            MeleeFighter mf = pcFighters.get(action.actor)
            if (mf.state.equals(FighterState.ACTIVE)) {
                // only active fighters' actions are executed
                executeAction(action)
            }
            mf.action = null // reset the melee action so it will not be automatically run again.
        }

        // check if fight is over
        if (fightIsOver(melee)) {
            notifyWinner(melee)
            melee.status = MeleeStatus.FINISHED
            findOrCreateMelee()
        }
        else {
            // surviving fighters: round++
            // set currentAction of all fighters to null
            melee.round++
            updateFighters(melee)
        }

    }

    void updateFighters(Melee melee) {
        melee.fighters.each {meleeFighter ->
            if (meleeFighter.state == FighterState.ACTIVE) {
                meleeFighter.action = null // reset melee action
                meleeFighter.round++
            }
        }
    }

    void notifyWinner(Melee melee) {
        MeleeFighter mf = melee.fighters.find {it.state == FighterState.ACTIVE}
        mf.state = FighterState.WINNER
        melee.winner = mf.pc
        mf.pc.currentMelee = null
        playerMessageService.createMessage(mf.pc, 'melee.you.won', null)
        melee.fighters.each {meleeFighter ->
            if (meleeFighter.state == FighterState.DEAD && meleeFighter.state != FighterState.WINNER) {
                // do not spam people who quit.
                playerMessageService.createMessage(meleeFighter.pc, 'melee.winner.is', [mf.pc.name])
            }
        }
        // TODO: update highscore, give bonus to GrandMelee Winner??
    }

    Boolean fightIsOver(Melee melee) {
        def remainingFighters = melee.fighters.findAll {it.state == FighterState.ACTIVE}
        return remainingFighters.size() < 2
    }

    /**
     * Compute the initiative value for all living fighters.
     * @return a list of MeleeActions sorted by initiative from highest to lowest.
     */
    List<MeleeAction> rollInitiative(Map<PlayerCharacter, MeleeFighter> pcFighters) {
        List<MeleeAction> actions = pcFighters.collect { pc, mf ->
            if (!mf.action) {
                mf.action = createDefaultAction(mf, pcFighters)
            }
            mf.action
        }
        actions.each {action ->
            action.initiative = action.actor.initiative.roll()
        }
        return actions.sort {it.initiative}.reverse()
    }

    void executeAction(MeleeAction action) {
        switch (action.type) {
            case (MeleeActionType.FIGHT): attack(action); break;
            case (MeleeActionType.USE_ITEM): useItem(action); break;
        }
    }

    void attack(MeleeAction action) {
        PlayerCharacter pc = action.actor
        PlayerCharacter adversary = action.target
        CombatMessage combatMessage = pc.attack(adversary)
        playerMessageService.createMessage(pc, combatMessage.msg, combatMessage.fetchArgs())
        playerMessageService.createMessage(adversary, combatMessage.msg, combatMessage.fetchArgs())
        // check effects:
        checkOpponentAlive(adversary, pc)
        checkOpponentAlive(pc, adversary)
    }

    /**
     * Use an item during a melee fight. If the selected feature is not available for
     * one reason or another, the player gets a message and forfeits his action.
     */
    void useItem(MeleeAction action) {
        PlayerCharacter pc = action.actor
        PlayerCharacter adversary = action.target

        Item item = action.item
        Feature itemFeature = action.feature
        ItemType itemType = item.type

        /*
         * Normally, we would check the following properties when the player
         * selects his melee action. But then a clever player is bound to select
         * an action and sell his item or use its feature before the melee is evaluated.
         */
        if (!pc.equals(item.owner)) {
            // tried to use an item that does not belong to him.
            playerMessageService.createMessage(pc, "melee.item.missing", [pc.name])
            return
        }
        if (!itemType.itemTypeFeatures?.find {it.feature.id == itemFeature.id}) {
            playerMessageService.createMessage(pc, "melee.item.missing.feature", [pc.name])
            return
        }

        // Is the item usable? Then execute its script.
        if (itemType.usable && item.uses > 0) {
            featureService.executeFeature(itemFeature, action.featureConfig, pc, [adversary], item)
            item.uses--
            if (item.uses == 0) {
                if (!itemType.rechargeable) {
                    action.setItem(null)
                    item.delete()
//                    action.save(flush:true)
//                    action = action.refresh()
//                    log.debug("action: ${action.dump()}")
                    /*
                     * Strange. item.delete fails even if the item is removed from action, player, type.
                     * Grails thinks it should be re-saved.
                     * This is one of the moments of "too-much-magic", as the only thing that seems
                     * to prevent the deletion is action.item (which *should* be null, but isn't).
                     * Going to try to delete items without uses in the controller after executing the melee.
                     *
                     */
//                    item.deleteFully()
                }
            }
        }
        else {
            playerMessageService.createMessage(pc, "melee.item.used.up", [pc.name])
        }

        // check effects:
        checkOpponentAlive(adversary, pc)
        checkOpponentAlive(pc, adversary)

    }

    void checkOpponentAlive(pc, adversary) {
        if (adversary.hp < 0) {
            adversary.alive = false
            adversary.deaths++
            MeleeFighter mf = MeleeFighter.findByPcAndMelee(adversary, pc.currentMelee)
            mf.state = FighterState.DEAD
            mf.pc.currentMelee = null
            notifyPlayerOfVictory(pc, adversary)
            notifyPlayerOfDeath(adversary, pc)
        }
    }

    /**
     * Send a "you are dead"-message to the player.
     */
    void notifyPlayerOfDeath(pc, adversary) {
        playerMessageService.createMessage(pc, 'melee.you.dead', [adversary.name])
    }

    /**
     * Send a "X is dead"-message to the survivor.
     */
    void notifyPlayerOfVictory(pc, adversary) {
        playerMessageService.createMessage(pc, 'melee.opponent.dead', [adversary.name])
    }


    void startMelee(Melee melee) {
        melee.status = MeleeStatus.RUNNING
    }

    Boolean meleeIsReady(Melee melee) {
        def minFighters = globalConfigService.fetchValueAsInt('melee.minimum.fighters', 2)
        if (melee.status == MeleeStatus.WAITING) {
            return melee.fighters?.size() >= minFighters
        }
        else {
            return false
        }
    }

    /**
     * The default action is to attack the first player available who is not the current player.
     * Note: this means a player can attack another with a higher initiative.
     */
    MeleeAction createDefaultAction(MeleeFighter mf, Map<PlayerCharacter, MeleeFighter> pcFighters) {
        def pc = mf.pc
        def target = pcFighters.values().find {
            it.state.equals(FighterState.ACTIVE) && !it.pc.equals(pc)
        }.pc
        MeleeAction defaultAction = new MeleeAction(actor: pc, target: target, melee: mf.melee)
        defaultAction.save()
        return defaultAction
    }

    Boolean checkAdversary(PlayerCharacter pc, PlayerCharacter opponent) {
        Melee melee = pc.currentMelee
        // first, we need a valid melee:
        if (!melee || melee.status != MeleeStatus.RUNNING) {
            return false
        }
        // a pc cannot attack himself (well, he could, but ... that's not a wholesome activity.
        if (pc.equals(opponent)) {
            return false
        }
        // check if both characters are part of the same melee:
        if (!opponent.currentMelee || !(pc.currentMelee == opponent.currentMelee)) {
            return false
        }
        // check that both pc and opponent are alive and still fighting:
        // (this *should* always be true at this point.
        if (!verifyPcIsActiveFighter(pc, melee) || !verifyPcIsActiveFighter(opponent, melee)) {
            return false
        }
        return true
    }

    Boolean verifyPcIsActiveFighter(pc, melee) {
        MeleeFighter mf = MeleeFighter.findByPcAndMelee(pc, melee)
        return mf && mf.state.equals(FighterState.ACTIVE)
    }

    /**
     * @return a player's current meleeAction (or null if he has not selected one yet)
     */
    MeleeAction fetchAction(pc) {
        def meleeFighter = fetchMeleeFighter(pc)
        if(meleeFighter && meleeFighter.action != null){
            return meleeFighter.action
        }
        else{
            return null
        }
    }

    /**
     * @param pc the PlayerCharacter whose MeleeFighter entry you need.
     * @return the MeleeFighter entry for the player character and his current melee (or null).
     */
    MeleeFighter fetchMeleeFighter(pc) {
        return MeleeFighter.find("from MeleeFighter mf where mf.pc=:pc and mf.melee=:melee", [pc: pc, melee: pc.currentMelee])
    }

    MeleeAction addAttackAction(pc, opponent) {
        Melee melee = pc.currentMelee
        if (fetchAction(pc)) {
            throw new RuntimeException('melee.action.selected')
        }
        MeleeAction attack = new MeleeAction(actor: pc, target: opponent, melee: melee)
        attack.save()
        fetchMeleeFighter(pc).action = attack
        return attack
    }

    MeleeAction addUseItemAction(pc, opponent, item, itemTypeFeature) {
        Melee melee = pc.currentMelee
        if (fetchAction(pc)) {
            throw new RuntimeException('melee.action.selected')
        }
        MeleeAction useItem = new MeleeAction(actor: pc, target: opponent,
                melee: melee, item: item, feature: itemTypeFeature.feature, featureConfig: itemTypeFeature.config, type: MeleeActionType.USE_ITEM)
        useItem.save()
        fetchMeleeFighter(pc).action = useItem
        return useItem
    }
    
    void tock(){
        Melee melee = Melee.findByStatus(MeleeStatus.RUNNING)
        if (melee) {
            fightMelee(melee)
        }
        else{
            melee = findOrCreateMelee()
            if (meleeIsReady(melee)) {
                startMelee(melee)
            }
        }
        log.debug("meleeStatus: ${melee.status} / round: ${melee.round}")
    }
}
