package de.dewarim.goblin.cron

import de.dewarim.goblin.MeleeStatus
import de.dewarim.goblin.combat.Melee

class CronController {

    def skillService
    def productionService
    def itemService
    def meleeService

    def beforeInterceptor = {
//      log.debug("requestURI:${request.requestURI}")
//      return request.getRemoteAddr().equals(request.getLocalAddr())
        def id = params.id
        log.debug("cronId: $id GoblinId: ${grailsApplication.config.goblinId}")
        boolean result = (id == grailsApplication.config.goblinId)
        log.debug("access is ${result ? 'granted' : 'denied'}")
        if (!result) {
            render(status: 503, text: 'You are not authorized to access this service!')
            return false
        }
    }

    def learning() {
        // process learning queue
        Integer amount = skillService.checkFinishedSkills()
        render(text: "processed: $amount skills.")
    }

    def makeProducts() {
        Integer items = productionService.makeProducts()
        itemService.cleanupItems()
        render(text: "created $items items.")
    }

    def melee() {
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
}
