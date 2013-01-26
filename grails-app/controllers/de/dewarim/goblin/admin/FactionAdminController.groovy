package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Creature
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.skill.CombatSkill
import de.dewarim.goblin.shop.ShopOwner
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.reputation.Faction
import de.dewarim.goblin.reputation.ReputationMessageMap

/**
 * Faction administration.
 */
@Secured(["ROLE_ADMIN"])
class FactionAdminController extends BaseController {

    def index() {
        return [
                factions: Faction.list(sort:params.sort ?: 'name', order: params.order ?: 'asc')
        ]
    }

    def edit() {
        def faction = Faction.get(params.id)
        if (!faction) {
            return render(status: 503, text: message(code: 'error.object.not.found'))
        }
        def rmmList = ReputationMessageMap.list().find{it.faction == null}
        render(template: '/factionAdmin/edit', model: [faction: faction, rmmList:rmmList])
    }

    def cancelEdit() {
        def faction = Faction.get(params.id)
        if (!faction) {
            return render(status: 503, text: message(code: 'error.object.not.found'))
        }
        render(template: '/factionAdmin/update', model: [faction: faction])
    }

    def update() {
        try {
            def faction = Faction.get(params.id)
            if (!faction) {
                throw new RuntimeException('error.object.not.found')
            }
            updateFields faction
            faction.save()
            render(template: '/factionAdmin/update', model: [faction: faction])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update faction:",e)
            renderException e
        }
    }

    protected void updateFields(faction){
        faction.name = inputValidationService.checkAndEncodeName(params.name ?: faction.name, faction)
        faction.description =
            inputValidationService.checkAndEncodeText(params, "description", "faction.description")
        faction.startLevel =
            inputValidationService.checkAndEncodeInteger(params, "start_level", "faction.start_level")
        if(params.repMessageMap){
            faction.repMessageMap =
                inputValidationService.checkObject(ReputationMessageMap.class, params.repMessageMap)
            faction.repMessageMap.faction = faction
            faction.repMessageMap.save()
        }
    }

    def save() {
        Faction faction = new Faction()
        try {
           updateFields(faction)
           faction.save()

           render(template: '/factionAdmin/list', model: [factions: Faction.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Faction faction = Faction.get(params.id)
        try {
            if (!faction) {
                throw new RuntimeException("error.object.not.found")
            }
            if(faction.repMessageMap){
                faction.repMessageMap.faction = null
                faction.repMessageMap.save(flush:true)
                faction.repMessageMap = null
                faction.save(flush:true)
            }
            faction.delete()
            render(text: message(code: 'faction.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
