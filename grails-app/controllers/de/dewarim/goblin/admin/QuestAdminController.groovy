package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.quest.QuestTemplate
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.town.Town
import de.dewarim.goblin.quest.QuestGiver

@Secured(["ROLE_ADMIN"])
class QuestAdminController extends BaseController {

    def inputValidationService

    def index = {
        return [
                questTemplates: QuestTemplate.listOrderByName()
        ]
    }

    def edit = {
        def questTemplate = QuestTemplate.get(params.id)
        if (!questTemplate) {
            return render(status: 503, text: message(code: 'error.unknown.questTemplate'))
        }
        render(template: 'edit', model: [questTemplate: questTemplate])
        return
    }

    def cancelEdit = {
        def questTemplate = QuestTemplate.get(params.id)
        if (!questTemplate) {
            return render(status: 503, text: message(code: 'error.unknown.questTemplate'))
        }
        render(template: 'update', model: [questTemplate: questTemplate])
        return
    }

    def update = {
        try {
            def questTemplate = QuestTemplate.get(params.id)
            if (!questTemplate) {
                throw new RuntimeException('error.unknown.questTemplate')
            }
            updateFields questTemplate
            questTemplate.save()
            render(template: 'list', model: [questTemplates: QuestTemplate.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    void updateFields(questTemplate) {
        questTemplate.name = inputValidationService.checkAndEncodeName(params.name, questTemplate)
        questTemplate.description =
                inputValidationService.checkAndEncodeText(params, "description", "questTemplate.description")
        questTemplate.level = inputValidationService.checkAndEncodeInteger(params, "level", "questTemplate.level")
        questTemplate.active = inputValidationService.checkAndEncodeBoolean(params, "active", "questTemplate.active")
        questTemplate.giver = inputValidationService.checkObject(QuestGiver.class, params.giver)
    }

    def save = {
        QuestTemplate questTemplate = new QuestTemplate()
        try {
            updateFields(questTemplate)
            questTemplate.save()
            render(template: 'list', model: [questTemplates: QuestTemplate.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete = {
        QuestTemplate questTemplate = QuestTemplate.get(params.id)
        try {
            if (!questTemplate) {
                throw new RuntimeException("error.object.not.found")
            }
            questTemplate.delete()
            render(text: message(code: 'questTemplate.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
