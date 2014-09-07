package de.dewarim.goblin.admin

import de.dewarim.goblin.GlobalConfigEntry
import de.dewarim.goblin.BaseController
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class ConfigAdminController extends BaseController {

    def index() {
        return [
                configEntries: GlobalConfigEntry.listOrderByName()
        ]
    }

    def edit() {
        def configEntry = GlobalConfigEntry.get(params.id)
        if (!configEntry) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/configAdmin/edit', model: [configEntry: configEntry])
    }

    def cancelEdit() {
        def configEntry = GlobalConfigEntry.get(params.id)
        if (!configEntry) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/configAdmin/update', model: [configEntry: configEntry])
    }

    def update() {
        try {
            def configEntry = GlobalConfigEntry.get(params.id)
            if (!configEntry) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${configEntry.name}")
            updateFields configEntry
            configEntry.save()
            render(template: '/configAdmin/update', model: [configEntry: configEntry])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update configEntry:",e)
            renderException e
        }
    }

    void updateFields(configEntry){
        configEntry.name = inputValidationService.checkAndEncodeName(params.name, configEntry)
        if(params.description){
            configEntry.description =
                inputValidationService.checkAndEncodeText(params, "description", "configEntry.description")
        }
        else{
            configEntry.description = null
        }
        if(params.entryValue){
            configEntry.entryValue =
                inputValidationService.checkAndEncodeText(params, "entryValue", "configEntry.entryValue")
        }
        else{
            configEntry.entryValue = null
        }
    }

    def save() {
        GlobalConfigEntry configEntry = new GlobalConfigEntry()
        try {
           updateFields(configEntry)
           configEntry.save()

           render(template: '/configAdmin/list', model: [configEntrys: GlobalConfigEntry.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save configEntry", e
            renderException(e)
        }
    }

    def delete() {
        GlobalConfigEntry configEntry = GlobalConfigEntry.get(params.id)
        try {
            if (!configEntry) {
                throw new RuntimeException("error.object.not.found")
            }
            configEntry.delete()
            log.debug("deleted configEntry. "+message(code:'configEntry.deleted'))
            render(text: message(code: 'configEntry.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
