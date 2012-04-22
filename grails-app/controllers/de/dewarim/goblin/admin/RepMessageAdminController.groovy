package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.reputation.ReputationMessageMap
import grails.plugins.springsecurity.Secured

import de.dewarim.goblin.reputation.ReputationMessage

@Secured(["ROLE_ADMIN"])
class RepMessageAdminController extends BaseController {

    def inputValidationService
    def rmmService

    def index = {
        try {
            def rmm = inputValidationService.checkObject(ReputationMessageMap.class, params.id)
            return [
                    rmm: rmm, repMessage: null
            ]
        }
        catch (Exception e) {
            flash.message = message(code: 'error.invalid.object')
            return redirect(controller: 'rmmAdmin', action: 'index')
        }
    }

    def edit = {
        try {
            def rm = inputValidationService.checkObject(ReputationMessage.class, params.id)
            render(template: 'edit', model: [rmm: rm.repMessageMap, repMessage: rm])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def cancelEdit = {
        try {
            def rm = inputValidationService.checkObject(ReputationMessage.class, params.id)
            render(template: 'update', model: [rmm: rm.repMessageMap, repMessage: rm])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def update = {
        try {
            def rm = inputValidationService.checkObject(ReputationMessage.class, params.id)
            updateFields(rm)
            rm.save()
            render(template: 'update', model: [rmm: rm.repMessageMap, repMessage: rm])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    /**
     * Add a message to a reputationMessageMap. #AJAX
     */
    def save = {
        try {
            ReputationMessageMap rmm = (ReputationMessageMap) inputValidationService.checkObject(ReputationMessageMap.class, params.rmm)
            ReputationMessage repMessage = new ReputationMessage(repMessageMap: rmm)
            updateFields(repMessage)
            rmm.addToRepMessages(repMessage)
            repMessage.save()
            render(template: 'list', model: [rmm: rmm, repMessage: repMessage])
        }
        catch (RuntimeException e) {
            renderException e
        }

    }

    /**
     * Delete a message from a reputationMessageMap. #AJAX
     */
    def delete = {
        try {
            ReputationMessage repMessage = (ReputationMessage) inputValidationService.checkObject(ReputationMessage, params.id)
            ReputationMessageMap rmm = repMessage.repMessageMap
            rmmService.removeMessage(repMessage)
            rmm.refresh()
            render(template: 'list', model: [rmm: rmm, repMessage: repMessage])
        }
        catch (RuntimeException e) {
            log.debug("failed to delete; ", e)
            renderException e
        }
    }

    protected void updateFields(ReputationMessage repMessage) {
        repMessage.reputation = inputValidationService.checkAndEncodeInteger(params, "reputation", "rmm.message.reputation")
        repMessage.messageId = inputValidationService.checkAndEncodeText(params, "messageId", "rmm.message.id")

        /*
         * We make sure that no two RMs have the same reputation level:
         */
        fixReputation(repMessage)
    }

    protected void fixReputation(ReputationMessage repMessage) {
        log.debug("lowerReputation")
        def rm = ReputationMessage.
                find("from ReputationMessage r where r.repMessageMap = ? and r.reputation = ?",
                [repMessage.repMessageMap, repMessage.reputation])
        if (rm) {
            Integer rep = rm.reputation
            rm.reputation = rep >= 0 ? rep + 1 : rep -1
            fixReputation(rm)
        }
    }

}
