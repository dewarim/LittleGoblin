package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.reputation.ReputationMessageMap
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.reputation.Faction

@Secured(["ROLE_ADMIN"])
class RmmAdminController extends BaseController {

    def inputValidationService
    def rmmService

    def index = {
        return [
                rmms: ReputationMessageMap.listOrderByName()
        ]
    }

    def edit = {
        def rmm = ReputationMessageMap.get(params.id)
        if (!rmm) {
            return render(status: 503, text: message(code: 'error.unknown.rmm'))
        }
        def factionList = Faction.list().findAll { (it.repMessageMap == null || it.repMessageMap?.equals(rmm))}
        render(template: '/rmmAdmin/edit', model: [rmm: rmm, factionList: factionList])
        return
    }

    def cancelEdit = {
        def rmm = ReputationMessageMap.get(params.id)
        if (!rmm) {
            return render(status: 503, text: message(code: 'error.unknown.rmm'))
        }
        render(template: '/rmmAdmin/update', model: [rmm: rmm])
        return
    }

    def update = {
        try {
            def rmm = ReputationMessageMap.get(params.id)
            if (!rmm) {
                throw new RuntimeException('error.unknown.rmm')
            }
            log.debug("update for ${rmm.name}")
            updateFields rmm
            rmm.save()
            render(template: '/rmmAdmin/list', model: [rmms: ReputationMessageMap.listOrderByName()])
        }
        catch (RuntimeException e) {
            log.debug("failed to update rmm:", e)
            renderException e
        }
    }

    void updateFields(rmm) {
        rmm.name = inputValidationService.checkAndEncodeName(params.name, rmm)
        if (params.faction?.equals('null') && rmm.faction) {
            rmmService.removeFaction(rmm)
        }
        if (params.faction && !params.faction.equals('null')) {
            def faction = inputValidationService.checkObject(Faction.class, params.faction)
            rmmService.removeFaction(rmm)
            rmmService.addFaction(rmm, faction)
        }
    }

    def save = {
        log.debug("save new rmm")
        ReputationMessageMap rmm = new ReputationMessageMap()
        try {
            updateFields(rmm)
            rmm.save()
            render(template: '/rmmAdmin/list', model: [rmms: ReputationMessageMap.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete = {
        try {
            ReputationMessageMap rmm = (ReputationMessageMap) inputValidationService.checkObject(ReputationMessageMap.class, params.id)
            if (rmm.faction) {
                rmmService.removeFaction(rmm)
            }
            rmm.delete()
            render(text: message(code: 'rmm.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
