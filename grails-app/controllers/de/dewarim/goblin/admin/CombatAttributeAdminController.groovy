package de.dewarim.goblin.admin

import de.dewarim.goblin.combat.CombatAttributeType
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController

@Secured(["ROLE_ADMIN"])
class CombatAttributeAdminController extends BaseController{

    def index() {
        if(params.indirectSubmit){
            flash.message = message(code:'warning.indirect.submit')
//            redirect(controller: 'combatAttributeAdmin', action: params.indirectSubmit, params:params)
        }
//        log(CombatAttributeType.list())
        return [
                attributeList:CombatAttributeType.listOrderByName()
        ]
    }

    def edit() {
        def combatAttribute = CombatAttributeType.get(params.attributeId)
        if(! combatAttribute){
            return render(status:503, text:message(code:'error.unknown.combatAttributeType'))
        }
        return render(template:'/combatAttributeAdmin/edit', model:[combatAttribute:combatAttribute])
    }

    def update() {
        try{
            def combatAttribute =  CombatAttributeType.get(params.attributeId)
            if(! combatAttribute){
                throw new RuntimeException('error.unknown.combatAttributeType')
            }
            combatAttribute.name = inputValidationService.checkAndEncodeName(params.name,combatAttribute)
            combatAttribute.save()
            render(template:'/combatAttributeAdmin/update', model:[combatAttribute:combatAttribute])
            // This would be the Grails way, except that it gives ugly I18N-error messages.
//            if(combatAttribute.save()){
//                render(template:'/combatAttributeAdmin/update', model:[combatAttribute:combatAttribute])
//            }
//            else{
//                render(template:'/shared/errors', model:[errorObject:combatAttribute])
//            }
        }
        catch (RuntimeException e){
            renderException e
        }
    }

    def save() {
        CombatAttributeType cat = new CombatAttributeType()
        try{
            cat.name = inputValidationService.checkAndEncodeName(params.name, cat)
            cat.save()
            render(template:'/combatAttributeAdmin/list', model:[attributeList:CombatAttributeType.listOrderByName()])
        }
        catch (RuntimeException e){
           renderException(e)
        }
    }

    def delete() {
        CombatAttributeType cat = CombatAttributeType.get(params.attributeId)
        try{
            if(! cat){
                throw new RuntimeException("error.object.not.found")
            }
            if(cat.weaponAttributes?.size() > 0){
                throw new RuntimeException("error.used_in_weaponAttribute")
            }
            if(cat.creatureAttributes?.size() > 0){
                throw new RuntimeException("error.used_in_creatureAttribute")
            }
            cat.delete()
            render(text:message(code:'combatAttributeType.deleted'))
        }
        catch (RuntimeException e){
            renderException e
        }
    }
        
}
