package de.dewarim.goblin;

import de.dewarim.goblin.quest.Quest;
import de.dewarim.goblin.quest.QuestTemplate
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.mob.Mob
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.quest.QuestStep
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.quest.QuestGiver

class QuestController extends BaseController {

    def questService
    def inputValidationService

    /**
     * Show the current quest and quest step
     */
    @Secured(['ROLE_USER'])
    def show = {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
        }
        if (!pc.currentQuest) {
            return redirect(controller: 'town', action: 'show', params: [pc: pc.id])
        }

        return [
                pc: pc,
                quest: pc.currentQuest
        ]
    }

    /**
     * Describe a quest prior to the user starting it
     */
    @Secured(['ROLE_USER'])
    def describeQuest = {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
        }
        if (pc.currentQuest) {
            // if the pc is already on a quest, show that:
            return redirect(controller: 'quest', action: 'show', params: [pc: pc.id])
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
    @Secured(['ROLE_USER'])
    def startQuest = {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
        }
        if (pc.currentQuest) {
            flash.message = message(code: 'error.already_questing')
            return redirect(controller: 'town', action: 'show', params: [pc: pc.id])
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
            return redirect(controller: 'fight', action: 'index', params: [// encounter:quest.currentStep.encounter.id,
                    combat: combat.id])
        }
        else {
            return redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
        }
    }

    /**
     * Start a new Quest
     */
    @Secured(['ROLE_USER'])
    def continueQuest = {
        def pc = fetchPc()
        if (! pc){
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
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
            return redirect(controller: 'fight', action: 'index', params: [// encounter:quest.currentStep.encounter.id,
                    combat: combat.id])
        }
        else {
            // this encounter is described in the quest step.
            return redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
//			flash.message = message(code:'error.encounterType.not.implemented')
            //			log.debug(flash.message)
        }
    }

    @Secured(['ROLE_USER'])
    def showStep = {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
        }
        Quest quest = pc.currentQuest
        if (!quest) {
            return redirect(controller: 'town', action: 'show', params: [pc: pc.id])
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

    @Secured(['ROLE_USER'])
    def finishQuest = {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
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
        return redirect(controller: 'town', action: 'show', params: [pc: pc.id])
    }

    @Secured(['ROLE_USER'])
    def nextStep = {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
        }
        Quest quest = pc.currentQuest
        QuestStep nextStep = QuestStep.get(params.step)
        if (!nextStep ||
                !pc.currentQuest.verifyNextStep(nextStep)) {
            flash.message = message(code: 'error.quest.step_not_found')
            return redirect(controller: 'town', action: 'show', params: [pc: pc.id])
        }
        quest.currentStep = nextStep
        return redirect(controller: 'quest', action: 'showStep', params: [pc: pc.id])
    }

    @Secured(['ROLE_USER'])
    def showQuestMaster = {
        def pc = fetchPc()
         if (!pc) {
            flash.message = message(code:'error.player_not_found')
            return redirect(controller: "portal", action: "start")
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
