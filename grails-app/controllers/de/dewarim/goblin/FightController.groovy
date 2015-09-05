package de.dewarim.goblin

import de.dewarim.goblin.fight.FightResult
import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Quest
import org.springframework.dao.OptimisticLockingFailureException

@Secured(['ROLE_USER'])
class FightController extends BaseController {

    def treasureService
    def fightService

    /*
      * Show opponent, option fight or flee.
      */

    def index() {
        def user = fetchUser()

        Combat combat = Combat.get(params.combat)
        if (!combat) {
            flash.message = message(code: 'error.combat.not_found')
            redirect(action: 'start', controller: 'portal')
            return
        }
        log.debug("pc.id: " + combat.playerCharacter.id)
        def pc = combat.playerCharacter
        if (!pc.user.equals(user)) {
            log.debug("could not find pc ${params}")
            redirect(controller: 'portal', action: 'start')
            return
        }
        log.debug("pc: " + pc)

        return [
                mob    : combat.fetchFirstMob(),
                pc     : pc,
                combat : combat,
                eqSlots: pc.fetchEquipment()
        ]
    }

    private MobTemplate selectRandomMobType() {
        List<MobTemplate> mtList = MobTemplate.list()
        return mtList.get((Integer) (Math.random() * mtList.size()))
    }

    def flee() {
        def user = fetchUser()

        Combat combat = Combat.get(params.combat)
        if (!combat) {
            flash.message = message(code: 'error.combat.not_found')
            redirect(action: 'start', controller: 'portal')
            return
        }

        def pc = combat.playerCharacter
        if (!pc.user.equals(user)) {
            // prevent cheating / using other player's combat.
            flash.message = message(code: 'error.foreign.object')
            redirect(action: 'start', controller: 'portal')
            return
        }

        if (!pc.currentQuest) {
            // pc has no current quest - probably reloaded this page.
            redirect(action: 'show', controller: 'town', params: [pc: pc.id])
            return
        }

        combat.finished = new Date()
        Quest quest = pc.currentQuest
        quest.finishedDate = new Date()
        quest.finished = true
        pc.currentQuest = null
        pc.currentCombat = null

        flash.message = message(code: 'fight.flee.success')
        return [pc   : pc,
                quest: quest
        ]
    }

    /*
      * Show fight result
      */

    def fight() {
        try {
            def user = fetchUser()

            flash.message = ""
            log.debug("request: " + params)
            PlayerCharacter pc = null
            Mob mob = null
            Combat combat = null
            Combat.withTransaction {
                combat = Combat.lock(params.combat)
                if (!combat) {
                    redirect(controller: 'portal', action: 'start')
                    return
                }
                pc = combat.playerCharacter

                if (!pc.user.equals(user)) {// make sure it is this user's combat
                    flash.message = message(code: 'error.foreign.object')
                    redirect(action: 'start', controller: 'portal')
                    return
                }
                mob = combat.fetchFirstMob()
                FightResult action
                try {
                    action = fightService.fight(combat, pc, mob)
                }
                catch (OptimisticLockingFailureException foo) {
                    // try once more: (a rather primitive approach...)
                    action = fightService.fight(combat, pc, mob)
                }

                switch (action) {
                    case FightResult.VICTORY: redirect(action: 'victory', params: [pc: pc.id, mob: mob.id, combat:
                            combat.id]); break
                    case FightResult.DEATH: redirect(action: 'death', params: [pc: pc.id, mob: mob.id, combat: combat
                            .id]); break
                    default: break;
                }

            }
            if (!request.isRedirected()) {
                return [pc    : pc,
                        mob   :
                                mob,
                        combat:
                                combat,
                ]
            }
        }
        catch (Exception e) {
            log.debug("Failed to fight.", e)
            throw new RuntimeException(e)
        }
    }

    def victory() {
        // TODO: set mob to dead.
        // TODO: add mob to pc.history
        // TODO: set queststep to finished, get next queststep or redirect to town.
        def user = fetchUser()
        def pc = fetchPc()
        def mob = Mob.get(params.mob)

        if (!pc.currentCombat) {
            redirect(controller: 'town', action: 'show', param: [pc: pc.id])
            return
        }

        pc.currentCombat.finished = new Date()
        pc.currentCombat = null

        pc.xp = pc.xp + mob.xpValue
        pc.victories = pc.victories + 1

        List<Item> treasure = treasureService.findTreasure(mob, pc)
//        treasure.each {
//            pc.addToItems(it)
//        }
        pc.save()

        // get next quest step
        Quest quest = pc.currentQuest
        quest.currentStep = quest.fetchNextQuestStep()
        if (quest.finished) {
            pc.currentQuest = null
            quest.successful = true
            pc.questLevel = pc.questLevel + 1
        }
        pc.currentCombat = null

        return [pc      : pc,
                mob     : mob,
                quest   : quest,
                treasure: treasure,
                combat  : Combat.get(params.combat)]
    }

    /*
      * Character is dead, show death message and high score.
      */

    def death() {
        def user = fetchUser()

        Combat combat = Combat.get(params.combat)
        if (!combat.playerCharacter.user.equals(user)) {
            // prevent cheating / using other player's combat.
            flash.message = message(code: 'error.foreign.object')
        }

        // TODO: set quest to finished (and and successful==false)
        def pc = combat.playerCharacter
//		def mob = combat.mobs.find{it==it} // TODO: handle multiple mobs

        pc.currentCombat.finished = new Date()
        pc.currentCombat = null
        pc.deaths = pc.deaths + 1

        pc.currentQuest.finishedDate = new Date()
        pc.currentQuest.finished = true
        Quest quest = pc.currentQuest
        pc.currentQuest = null

        Mob mob = combat.fetchFirstMob()
        HighScore hs = new HighScore(character: pc, xp: pc.xp, killerMob: mob)
        hs.save()

        return [pc    : pc,
                mob   : mob,
                quest : quest,
                combat: Combat.get(params.combat)]
    }

    def highscore() {
        def pc = PlayerCharacter.get(params.pc)
        // def mob = Mob.get(params.mob)

        return [pc       : pc,
                highscore: HighScore.list(sort: 'xp', order: 'desc')
        ]
    }

}
