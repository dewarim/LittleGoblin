package de.dewarim.goblin;

import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.quest.Quest
import de.dewarim.goblin.item.Item

import grails.plugins.springsecurity.Secured

import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.pc.PlayerCharacter


class FightController extends BaseController {
    
    def treasureService
    def fightService

    /*
      * Show opponent, option fight or flee.
      */
    @Secured(['ROLE_USER'])
    def index = {
        def user = fetchUser()

        Combat combat = Combat.get(params.combat)
        if (!combat) {
            flash.message = message(code: 'error.combat.not_found')
            return redirect(action: 'start', controller: 'portal')
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
                mob: combat.fetchFirstMob(),
                pc: pc,
                combat: combat,
                eqSlots: pc.fetchEquipment()
        ]
    }

    MobTemplate selectRandomMobType() {
        List<MobTemplate> mtList = MobTemplate.list()
        return mtList.get((Integer) (Math.random() * mtList.size()))
    }

    @Secured(['ROLE_USER'])
    def flee = {
        def user = fetchUser()

        Combat combat = Combat.get(params.combat)
        if (!combat) {
            flash.message = message(code: 'error.combat.not_found')
            return redirect(action: 'start', controller: 'portal')
        }

        def pc = combat.playerCharacter
        if (!pc.user.equals(user)) {
            // prevent cheating / using other player's combat.
            flash.message = message(code: 'error.foreign.object')
            return redirect(action: 'start', controller: 'portal')
        }

        if (!pc.currentQuest) {
            // pc has no current quest - probably reloaded this page.
            return redirect(action: 'show', controller: 'town', params: [pc: pc.id])
        }

        combat.finished = new Date()
        Quest quest = pc.currentQuest
        quest.finishedDate = new Date()
        quest.finished = true
        pc.currentQuest = null
        pc.currentCombat = null

        flash.message = message(code: 'fight.flee.success')
        return [pc: pc,
                quest: quest
        ]
    }

    /*
      * Show fight result
      */
    @Secured(['ROLE_USER'])
    def fight = {
        def user = fetchUser()

        flash.message = ""
        log.debug("request: " + params)
        PlayerCharacter pc
        Mob mob
        Combat combat
        Combat.withTransaction {
            combat = Combat.lock(params.combat)
            if (!combat) {
                return redirect(controller: 'portal', action: 'start')
            }
            pc = combat.playerCharacter

            if (!pc.user.equals(user)) {// make sure it is this user's combat
                flash.message = message(code: 'error.foreign.object')
                return redirect(action: 'start', controller: 'portal')
            }
            mob = combat.fetchFirstMob()
            String action
            try {
                action = fightService.fight(combat, pc, mob)
            }
            catch (org.springframework.dao.OptimisticLockingFailureException foo) {
                // try once more: (a rather primitive approach...)
                action = fightService.fight(combat, pc, mob)
            }

            if (action) {
                return redirect(action: action, params: [pc: pc.id, mob: mob.id, combat: combat.id])
            }
        }
        return [pc: pc,
                mob: mob,
                combat: combat,
        ]
    }

    @Secured(['ROLE_USER'])
    def victory = {
        // TODO: set mob to dead.
        // TODO: add mob to pc.history
        // TODO: set queststep to finished, get next queststep or redirect to town.
        def user = fetchUser()
        def pc = fetchPc()
        def mob = Mob.get(params.mob)

        if (!pc.currentCombat) {
            return redirect(controller: 'town', action: 'show', param: [pc: pc.id])
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

        return [pc: pc,
                mob: mob,
                quest: quest,
                treasure: treasure,
                combat: Combat.get(params.combat)]
    }

    /*
      * Charakter is dead, show death message and high score.
      */
    @Secured(['ROLE_USER'])
    def death = {
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
        pc.alive = false
        pc.deaths = pc.deaths + 1

        pc.currentQuest.finishedDate = new Date()
        pc.currentQuest.finished = true
        Quest quest = pc.currentQuest
        pc.currentQuest = null

        Mob mob = combat.fetchFirstMob()
        HighScore hs = new HighScore(character: pc, xp: pc.xp, killer: mob)
        hs.save()

        return [pc: pc,
                mob: mob,
                quest: quest,
                combat: Combat.get(params.combat)]
    }

    def highscore = {
        def pc = PlayerCharacter.get(params.pc)
        // def mob = Mob.get(params.mob)

        return [pc: pc,
                highscore: HighScore.list(sort: 'xp', order: 'desc')
        ]
    }

}
