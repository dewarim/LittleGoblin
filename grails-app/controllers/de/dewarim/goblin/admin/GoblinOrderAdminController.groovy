package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.town.Academy
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.validation.ObjectError

@Secured(["ROLE_ADMIN"])
class GoblinOrderAdminController extends BaseController {

    def index() {
        return [
                goblinOrders: GoblinOrder.listOrderByName()
        ]
    }

    def edit(Long id) {
        def goblinOrder = GoblinOrder.get(id)
        if (!goblinOrder) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/goblinOrderAdmin/edit', model: [goblinOrder: goblinOrder])
    }

    def cancelEdit(Long id) {
        def goblinOrder = GoblinOrder.get(id)
        if (!goblinOrder) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/goblinOrderAdmin/update', model: [goblinOrder: goblinOrder])
    }

    def update(Long id) {
        try {
            def goblinOrder = GoblinOrder.get(id)
            if (!goblinOrder) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${goblinOrder.name}")
            bindData(goblinOrder, params, [include: ['name', 'description', 'score', 'coins']])
            def leader = PlayerCharacter.get(params.get('leader.id'))
            goblinOrder.leader = leader
            if (!goblinOrder.validate()) {
                goblinOrder.errors.allErrors.each { error ->
                }
//                throw new RuntimeException()
            }
            goblinOrder.save()
            render(template: '/goblinOrderAdmin/update', model: [goblinOrder: goblinOrder])
        }
        catch (RuntimeException e) {
            log.debug("failed to update goblinOrder:", e)
            renderException e
        }
    }

    def save() {
        GoblinOrder goblinOrder = new GoblinOrder()
        log.debug("params: ${params.dump()}")
        try {
            bindData(goblinOrder, params, [include: ['name', 'description', 'score', 'coins']])
            def leader = PlayerCharacter.get(params.get('leader.id'))
            goblinOrder.leader = leader
            if (!goblinOrder.validate()) {
                render(status: 503, template: 'errors', model:[goblinOrder:goblinOrder])
                return
            }
            goblinOrder.save()
            render(template: '/goblinOrderAdmin/list', model: [goblinOrders: GoblinOrder.listOrderByName()])
        }
        catch (RuntimeException e) {
            log.debug "failed to save goblinOrder", e
            renderException(e)
        }
    }

    def delete(Long id) {
        GoblinOrder goblinOrder = GoblinOrder.get(id)
        try {
            if (!goblinOrder) {
                throw new RuntimeException("error.object.not.found")
            }
            goblinOrder.deleteComplete()
            render(text: message(code: 'goblinOrder.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
