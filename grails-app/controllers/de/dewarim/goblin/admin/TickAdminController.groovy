package de.dewarim.goblin.admin

import de.dewarim.goblin.ticks.Tick
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(["ROLE_ADMIN"])
class TickAdminController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tickList: Tick.list(params), tickTotal: Tick.count()]
    }

    def create() {
        [tick: new Tick(params)]
    }

    def save() {
        def tick = new Tick(params)
        if (!tick.save(flush: true)) {
            render(view: "create", model: [tick: tick])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tick.label', default: 'Tick'), tick.id])
        redirect(action: "show", id: tick.id)
    }

    def show(Long id) {
        def tick = Tick.get(id)
        if (!tick) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "list")
            return
        }

        [tick: tick]
    }

    def edit(Long id) {
        def tick = Tick.get(id)
        if (!tick) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "list")
            return
        }

        [tick: tick]
    }

    def update(Long id, Long version) {
        def tick = Tick.get(id)
        if (!tick) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tick.version > version) {
                tick.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tick.label', default: 'Tick')] as Object[],
                          "Another user has updated this Tick while you were editing")
                render(view: "edit", model: [tick: tick])
                return
            }
        }

        tick.properties = params

        if (!tick.save(flush: true)) {
            render(view: "edit", model: [tick: tick])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tick.label', default: 'Tick'), tick.id])
        redirect(action: "show", id: tick.id)
    }

    def delete(Long id) {
        def tick = Tick.get(id)
        if (!tick) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "list")
            return
        }

        try {
            tick.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tick.label', default: 'Tick'), id])
            redirect(action: "show", id: id)
        }
    }
}
