package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.quest.QuestGiver
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class QuestGiverAdminController extends BaseController {

    def inputValidationService

    def index() {
        return [
                questGivers: QuestGiver.listOrderByName()
        ]
    }

    def edit() {
        def questGiver = QuestGiver.get(params.id)
        if (!questGiver) {
            return render(status: 503, text: message(code: 'error.unknown.questGiver'))
        }
        render(template: 'edit', model: [questGiver: questGiver])
        return
    }

    def cancelEdit() {
        def questGiver = QuestGiver.get(params.id)
        if (!questGiver) {
            return render(status: 503, text: message(code: 'error.unknown.questGiver'))
        }
        render(template: 'update', model: [questGiver: questGiver])
        return
    }

    def update() {
        try {
            def questGiver = QuestGiver.get(params.id)
            if (!questGiver) {
                throw new RuntimeException('error.unknown.questGiver')
            }
            updateFields questGiver
            questGiver.save()
            render(template: 'list', model: [questGivers: QuestGiver.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(questGiver) {
        questGiver.name = inputValidationService.checkAndEncodeName(params.name, questGiver)
        questGiver.description =
                inputValidationService.checkAndEncodeText(params, "description", "questGiver.description")
    }

    def save() {
        QuestGiver questGiver = new QuestGiver()
        try {
            updateFields(questGiver)
            questGiver.save()
            render(template: 'list', model: [questGivers: QuestGiver.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        QuestGiver questGiver = QuestGiver.get(params.id)
        try {
            if (!questGiver) {
                throw new RuntimeException("error.object.not.found")
            }
            if( questGiver.templates.size() > 0){
                throw new RuntimeException(("error.questGiver.in.use"))
            }
            questGiver.delete()
            render(text: message(code: 'questGiver.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
