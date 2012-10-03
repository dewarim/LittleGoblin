package de.dewarim.goblin.cron

import de.dewarim.goblin.combat.Melee
import de.dewarim.goblin.MeleeStatus

class CronController {

    def skillService
    def productionService
    def itemService
    def meleeService

    def learning() {
        if (requestAllowed()) {
            // process learning queue
            Integer amount = skillService.checkFinishedSkills()
            render(text: "processed: $amount skills.")
        }
        else {
            sendAway()
        }
    }

    def makeProducts() {
        if (requestAllowed()) {
            Integer items = productionService.makeProducts()
            itemService.cleanupItems()
            render(text: "created $items items.")
        }
        else {
            sendAway()
        }
    }

    def melee() {
        if (requestAllowed()) {
            Melee melee = Melee.findByStatus(MeleeStatus.RUNNING)
            if (melee) {
                meleeService.fightMelee(melee)
            }
            else{
                melee = meleeService.findOrCreateMelee()
                if (meleeService.meleeIsReady(melee)) {
                    meleeService.startMelee(melee)
                }
            }
            render(text: "meleeStatus: ${melee.status} / round: ${melee.round}")
        }
        else {
            sendAway()
        }
    }

    protected Boolean requestAllowed() {
//      log.debug("requestURI:${request.requestURI}")
//      return request.getRemoteAddr().equals(request.getLocalAddr())
        def id = params.id
        log.debug("cronId: $id GoblinId: ${grailsApplication.config.goblinId}")
        def result = (id == grailsApplication.config.goblinId)
        log.debug("access is ${result ? 'granted' : 'denied'}")
        return result
    }

    protected void sendAway() {
        render(status: 503, text: 'You are not authorized to access this service!')
    }
}
