package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.mob.MobTemplate
import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.Dice
import de.dewarim.goblin.Image
import de.dewarim.goblin.mob.MobImage

@Secured(["ROLE_ADMIN"])
class MobAdminController extends BaseController {

    def index() {
        return [
                mobs: MobTemplate.listOrderByName()
        ]
    }

    def edit() {
        def mob = MobTemplate.get(params.id)
        if (!mob) {
            render(status: 503, text: message(code: 'error.unknown.mob'))
            return
        }
        def imageList = mob.mobImages.collect {it.image}
        render(template: 'edit', model: [mob: mob, imageList:imageList])
    }

    def cancelEdit() {
        def mob = MobTemplate.get(params.id)
        if (!mob) {
            render(status: 503, text: message(code: 'error.unknown.mob'))
            return
        }
        render(template: 'update', model: [mob: mob])
    }

    def update() {
        try {
            def mob = MobTemplate.get(params.id)
            if (!mob) {
                throw new RuntimeException('error.unknown.mob')
            }
            updateFields mob
            mob.save()
            render(template: 'list', model: [mobs: MobTemplate.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(e) {
        MobTemplate mob = (MobTemplate) e
        mob.name = inputValidationService.checkAndEncodeName(params.name, mob)
        if(params.description){
            mob.description = inputValidationService.checkAndEncodeText(params, 'description', 'description')
        }
        else{
            mob.description = ''
        }

        mob.strike = (Dice) inputValidationService.checkObject(Dice.class, params.strike, true) ?: null
        mob.parry =  (Dice) inputValidationService.checkObject(Dice.class, params.parry, true) ?: null
        mob.damage = (Dice) inputValidationService.checkObject(Dice.class, params.damage, true) ?: null
        mob.initiative = (Dice) inputValidationService.checkObject(Dice.class, params.initiative, true) ?: null
        mob.xpValue = inputValidationService.checkAndEncodeInteger(params, "xpValue", "mob.xpValue")
        mob.hp = inputValidationService.checkAndEncodeInteger(params, "hp", "mob.hp")
        mob.maxHp = inputValidationService.checkAndEncodeInteger(params, "maxHp", "mob.maxHp")
        mob.gold = inputValidationService.checkAndEncodeInteger(params, "gold", "mob.gold")
        mob.male = inputValidationService.checkAndEncodeBoolean(params, "male", 'mob.male')

        def images = params.list("image").collect {inputValidationService.checkObject(Image.class, it)}
        // remove no longer used images
        mob.mobImages.each {mobImage ->
            if (!images.contains(mobImage.image)) {
                log.debug("delete unused image "+mobImage.image.name)
                mobImage.deleteFully()
            }
        }
        // add new mobs
        images.each{Image image ->
            if(! mob.mobImages.find{it.image.equals(image)} ){
                log.debug("add new image "+image.name)
                MobImage mobImage = new MobImage(mob, image)
                mobImage.save()
            }
        }

    }

    def save() {
        MobTemplate mob = new MobTemplate()
        try {
            updateFields(mob)
            mob.save()
            render(template: 'list', model: [mobs: MobTemplate.listOrderByName()])
        }
        catch (RuntimeException e) {
            log.debug("failed to save MobTemplate: ",e)
            renderException(e)
        }
    }

    def delete() {
        MobTemplate mob = MobTemplate.get(params.id)
        try {
            if (!mob) {
                throw new RuntimeException("error.object.not.found")
            }
            mob.delete()
            render(text: message(code: 'mob.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
