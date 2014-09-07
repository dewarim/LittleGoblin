package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.quest.QuestStep
import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.quest.Encounter
import de.dewarim.goblin.quest.QuestTemplate
import de.dewarim.goblin.quest.StepChild

@Secured(["ROLE_ADMIN"])
class QuestStepAdminController extends BaseController {

    def index() {
        return [
                questSteps: QuestStep.listOrderByName()
        ]
    }

    def edit() {
        def questStep = QuestStep.get(params.id)
        if (!questStep) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        def newSteps = QuestStep.findAll("from QuestStep s where s.questTemplate=:template and s!=:self",
                [template:questStep.questTemplate, self:questStep])
        def childList = questStep.nextSteps.collect {it.child}
        def parentList = questStep.parentSteps.collect {it.parent}
        render(template: 'edit', model: [
                questStep: questStep, childSteps: childList, parentSteps: parentList, newSteps:newSteps
        ])
    }

    def cancelEdit() {
        def questStep = QuestStep.get(params.id)
        if (!questStep) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: 'update', model: [questStep: questStep])
    }

    def update() {
        try {
            def questStep = QuestStep.get(params.id)
            if (!questStep) {
                throw new RuntimeException('error.object.not.found')
            }
            updateFields questStep
            questStep.save()
            render(template: 'list', model: [questSteps: QuestStep.listOrderByName()])
        }
        catch (RuntimeException e) {
            log.debug("failed to update",e)
            renderException e
        }
    }

    protected void updateFields(qStep) {
        QuestStep questStep = (QuestStep) qStep // casting to QuestStep so IDE will provide method suggestions
        questStep.name = inputValidationService.checkAndEncodeName(params.name, questStep)
        questStep.description =
            inputValidationService.checkAndEncodeText(params, "description", "questStep.description")
        questStep.title =
            inputValidationService.checkAndEncodeText(params, "title", "questStep.title")
        if (params.intro) {
            questStep.intro =
                inputValidationService.checkAndEncodeText(params, "intro", "questStep.intro")
        }
        else {
            questStep.intro = null
        }
        questStep.endOfQuest =
            inputValidationService.checkAndEncodeBoolean(params, "endOfQuest", "questStep.endOfQuest")
        questStep.encounter =
            (Encounter) inputValidationService.checkObject(Encounter.class, params.encounter)
        QuestTemplate newTemplate =
            (QuestTemplate) inputValidationService.checkObject(QuestTemplate.class, params.questTemplate)
        if(! questStep.questTemplate.equals(newTemplate)){
            // quest template changed, we have to remove all related quest steps (as there is
            // currently no easy way to untangle complex quest chains)
            questStep.nextSteps.each{step ->
                step.deleteFully()
            }
            questStep.parentSteps.each{step ->
                step.deleteFully()
            }
            questStep.questTemplate = newTemplate
        }

        def children = params.list("nextStep").collect {step ->
//            log.debug("collecting: $step")
            inputValidationService.checkObject(QuestStep.class, step)
        }
        def deleteChildren = []
        questStep.nextSteps.each {stepChild ->
            if (!children.contains(stepChild.child)) {
                log.debug("child toDelete: $stepChild.id")
                deleteChildren.add(stepChild)
            }
        }
        deleteChildren.each{child ->
            child.deleteFully()
        }
        deleteChildren = []

        children.each {QuestStep child ->
//            log.debug("examining child: ${child.id} ${child.name}")
            if (!questStep.nextSteps.find {it.child.equals(child)}) {
                questStep.save()
                log.debug("add nextStep with ${questStep.id} ${child.id}")
                StepChild stepChild = new StepChild(questStep, child)
                stepChild.save()
            }
        }
        def parents = params.list("parentStep").collect { inputValidationService.checkObject(QuestStep.class, it)}
        questStep.parentSteps.each {stepParent ->
            if (!parents.contains(stepParent.parent)) {
                log.debug("parent toDelete: $stepParent.id")
                deleteChildren.add(stepParent)
            }
        }
        deleteChildren.each{child ->
            child.deleteFully()
        }
        parents.each {QuestStep parent ->
            log.debug("examining parent: ${parent.id} ${parent.name}")
            if (! questStep.parentSteps.find {it.parent.equals(parent)}) {
                questStep.save()
                log.debug("add parentStep with ${parent.id} ${questStep.id}")
                StepChild stepParent = new StepChild(parent, questStep)
                stepParent.save()
            }
        }

    }

    def save() {
        QuestStep questStep = new QuestStep()
        try {
            updateFields(questStep)
            questStep.save()
            render(template: 'list', model: [questSteps: QuestStep.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        QuestStep questStep = QuestStep.get(params.id)
        try {
            if (!questStep) {
                throw new RuntimeException("error.object.not.found")
            }
            if (questStep.templates.size() > 0) {
                throw new RuntimeException(("error.questStep.in.use"))
            }
            questStep.delete()
            render(text: message(code: 'object.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
