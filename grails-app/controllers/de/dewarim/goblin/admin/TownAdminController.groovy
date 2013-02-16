package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.town.Town
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.town.Academy

@Secured(["ROLE_ADMIN"])
class TownAdminController extends BaseController {

    def index() {
        return [
                towns: Town.listOrderByName()
        ]
    }

    def edit() {
        def town = Town.get(params.id)
        if (!town) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/townAdmin/edit', model: [town: town])
    }

    def cancelEdit() {
        def town = Town.get(params.id)
        if (!town) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/townAdmin/update', model: [town: town])
    }

    def update() {
        try {
            def town = Town.get(params.id)
            if (!town) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${town.name}")
            updateFields town
            town.save()
            render(template: '/townAdmin/update', model: [town: town])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update town:",e)
            renderException e
        }
    }

    protected void updateFields(town){
        town.name = inputValidationService.checkAndEncodeName(params.name, town)
        town.description =
            inputValidationService.checkAndEncodeText(params, "description", "town.description")
        town.shortDescription =
            inputValidationService.checkAndEncodeText(params, "shortDescription", "town.shortDescription")
    }

    def save() {
        Town town = new Town()
        try {
           updateFields(town)
           town.save()

           render(template: '/townAdmin/list', model: [towns: Town.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save town", e
            renderException(e)
        }
    }

    def delete() {
        Town town = Town.get(params.id)
        try {
            if (!town) {
                throw new RuntimeException("error.object.not.found")
            }
            if( town.homes){
                throw new RuntimeException("error.town.inhabited")
            }
            town.delete()
            render(text: message(code: 'town.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
