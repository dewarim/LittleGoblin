package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.quest.Quest
import de.dewarim.goblin.quest.QuestGiver
import de.dewarim.goblin.quest.QuestStep
import de.dewarim.goblin.quest.QuestTemplate

@Secured(['ROLE_USER'])
class QuestController extends BaseController {

    def questService

    /**
     * Show the current quest and quest step
     */
    def show() {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        if (!pc.currentQuest) {
            redirect(controller: 'town', action: 'show', params: [pc: pc.id])
            return
        }

        return [
                pc: pc,
                quest: pc.currentQuest
        ]
    }

    /**
     * Describe a quest prior to the user starting it
     */
    def describeQuest() {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        if (pc.currentQuest) {
            // if the pc is already on a quest, show that:
            redirect(controller: 'quest', action: 'show', params: [pc: pc.id])
            return
        }

        QuestTemplate qt = QuestTemplate.get(params.quest)

        return [
                pc: pc,
                qt: qt
        ]
    }

    /**
     * Start a new Quest
     */
    def startQuest() {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        if (pc.currentQuest) {
            flash.message = message(code: 'error.already_questing')
            redirect(controller: 'town', action: 'show', params: [pc: pc.id])
            return
        }
        QuestTemplate qt = QuestTemplate.get(params.quest)
        // TODO: validate access to this quest for this pc.

        Quest quest = new Quest(template: qt, playerCharacter: pc)
        quest.initializeQuest()
        quest.save(failOnErrors: true)
        pc.currentQuest = quest

        // TODO: this is not really best OOP-practice,
        // to reach down into the guts of the quest to retrieve the combat.
        if (quest.currentStep?.encounter?.includesCombat) {
            // TODO: handle multiple mobs - at the moment, this just picks the first one.
            MobTemplate mt = quest.currentStep.encounter.mobs.find {true}.collect {it.mob}.get(0) // ugly. TODO: refactor.
            Mob mob = new Mob(name: mt.name, type: mt)
            mob.initMob()
            mob.save()
            log.debug("created new mob: ${mob.dump()}")
            def combat = new Combat(pc, mob)
            pc.currentCombat = combat
            combat.save(failOnError: true)
            log.debug('redirecting to fight.index')
            redirect(controller: 'fight', action: 'index', params: [// encounter:quest.currentStep.encounter.id,
                    combat: combat.id])
        }
        else {
            redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
        }
    }

    /**
     * Start a new Quest
     */
    def continueQuest() {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        //	QuestTemplate qt = QuestTemplate.get(params.quest)
        // TODO: validate access to this quest for this pc.
        Quest quest = pc.currentQuest

        // TODO: refactor with startQuest - prevent further copy&paste
        // TODO: this is not really best OOP-practice,
        // to reach down into the guts of the quest to retrieve the combat.
        if (quest.currentStep?.encounter?.includesCombat) {
            // TODO: handle multiple mobs - at the moment, this just picks the first one.
            def mobs = quest.currentStep.encounter.mobs
            MobTemplate mt = mobs.find {true}.mob // ugly. TODO: refactor.
            Mob mob = new Mob(name: mt.name, type: mt)
            mob.initMob()
            mob.save()
            def combat = new Combat(pc, mob)
            pc.currentCombat = combat
            combat.save(failOnError: true)
            log.debug('redirecting to fight.index')
            redirect(controller: 'fight', action: 'index', params: [// encounter:quest.currentStep.encounter.id,
                    combat: combat.id])
        }
        else {
            // this encounter is described in the quest step.
            redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
        }
    }

    def showStep() {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        Quest quest = pc.currentQuest
        if (!quest) {
            redirect(controller: 'town', action: 'show', params: [pc: pc.id])
            return
        }

        QuestStep currentStep = quest.currentStep
        if (quest.lastExecutedStep?.equals(currentStep)) {
            log.debug("script was already executed.")
        }
        else {
            questService.executeEncounterScript(currentStep, pc)
            quest.lastExecutedStep = currentStep
        }

        return [pc: pc,
                step: currentStep]
    }

    def finishQuest() {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        Quest quest = pc.currentQuest
        if (quest) {
            quest.finishedDate = new Date()
            quest.finished = true
            quest.currentStep = null
            pc.currentQuest = null
            pc.questLevel = pc.questLevel + 1
            quest.successful = true
        }
        redirect(controller: 'town', action: 'show', params: [pc: pc.id])
    }

    def nextStep() {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        Quest quest = pc.currentQuest
        QuestStep nextStep = QuestStep.get(params.step)
        if (!nextStep ||
                !pc.currentQuest.verifyNextStep(nextStep)) {
            flash.message = message(code: 'error.quest.step_not_found')
            redirect(controller: 'town', action: 'show', params: [pc: pc.id])
            return
        }
        quest.currentStep = nextStep
        redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
    }

    def showQuestMaster() {
        def pc = fetchPc()
         if (!pc) {
            flash.message = message(code:'error.player_not_found')
            redirect(controller: "portal", action: "start")
            return
        }
        QuestGiver questMaster = (QuestGiver) inputValidationService.checkObject(QuestGiver.class, params.questMaster, true)
        if(! questMaster){
            flash.message = message(code:'error.questMaster.not_found')
        }
        return [
                quests:questService.fetchAvailableQuestsByQuestGiver(questMaster, pc),
                questMaster:questMaster,
                pc:pc
        ]
    }
}
