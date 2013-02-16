package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.quest.Encounter
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.mob.EncounterMob

@Secured(["ROLE_ADMIN"])
class EncounterAdminController extends BaseController {

    def index() {
        return [
                encounters: Encounter.listOrderByName()
        ]
    }

    def edit() {
        def encounter = Encounter.get(params.id)
        if (!encounter) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        def mobList = encounter.mobs.collect {it.mob}
        render(template: 'edit', model: [encounter: encounter, mobList: mobList])
    }

    def cancelEdit() {
        def encounter = Encounter.get(params.id)
        if (!encounter) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: 'update', model: [encounter: encounter])
    }

    def update() {
        try {
            def encounter = Encounter.get(params.id)
            if (!encounter) {
                throw new RuntimeException('error.object.not.found')
            }
            updateFields encounter
            encounter.save()
            render(template: 'list', model: [encounters: Encounter.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(e) {
        Encounter encounter = (Encounter) e
        encounter.name = inputValidationService.checkAndEncodeName(params.name, encounter)
        encounter.includesCombat = inputValidationService.checkAndEncodeBoolean(params, 'includesCombat', 'encounter.includesCombat')
        if (params.script) {
            encounter.script = (GoblinScript) inputValidationService.checkObject(GoblinScript.class, params.script)
        }
        else {
            encounter.script = null
        }
        encounter.config = params.config // do not encode xml on input side (but must encode on display!)
        def mobs = params.list("mob").collect {inputValidationService.checkObject(MobTemplate.class, it)}
        // remove no longer used mobs
        encounter.mobs.each {encounterMob ->
            if (!mobs.contains(encounterMob.mob)) {
                log.debug("delete unused mob "+encounterMob.mob.name)
                encounter.removeFromMobs(encounterMob)
                encounterMob.mob.removeFromEncounterMobs(encounterMob)
                encounterMob.delete()
            }
        }
        // add new mobs
        mobs.each {MobTemplate mob ->
            if(! encounter.mobs.find{it.mob.equals(mob)}){
                log.debug("add new mob "+mob.name)
                EncounterMob em = new EncounterMob(encounter, mob)
                em.save()
            }
        }
    }

    def save() {
        Encounter encounter = new Encounter()
        try {
            updateFields(encounter)
            encounter.save()
            render(template: 'list', model: [encounters: Encounter.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Encounter encounter = Encounter.get(params.id)
        try {
            if (!encounter) {
                throw new RuntimeException("error.object.not.found")
            }
            encounter.delete()
            render(text: message(code: 'object.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
