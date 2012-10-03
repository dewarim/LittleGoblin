package de.dewarim.goblin.admin

import de.dewarim.goblin.EquipmentSlotType
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController

@Secured(["ROLE_ADMIN"])
class EquipmentSlotTypeAdminController extends BaseController {

    def inputValidationService

    def index() {
        return [
                slotList:EquipmentSlotType.listOrderByName()
        ]
    }

    def edit() {
        def equipmentSlot = EquipmentSlotType.get(params.slotId)
        if(! equipmentSlot){
            return render(status:503, text:message(code:'error.unknown.equipmentSlotType'))
        }
        return render(template:'/equipmentSlotTypeAdmin/edit', model:[equipmentSlotType:equipmentSlot])
    }

    def update() {
        try{
            def equipmentSlot =  EquipmentSlotType.get(params.slotId)
            if(! equipmentSlot){
                throw new RuntimeException('error.unknown.equipmentSlotType')
            }
            log.debug("update for ${equipmentSlot.name}")
            equipmentSlot.name = inputValidationService.checkAndEncodeName(params.name,equipmentSlot)

            equipmentSlot.save()
            render(template:'/equipmentSlotTypeAdmin/update', model:[equipmentSlotType:equipmentSlot])
        }
        catch (RuntimeException e){
            renderException e
        }
    }

    def save() {
        EquipmentSlotType slotType = new EquipmentSlotType()
        try{
            slotType.name = inputValidationService.checkAndEncodeName(params.name, slotType)
            slotType.save()
            render(template:'/equipmentSlotTypeAdmin/list', model:[slotList:EquipmentSlotType.listOrderByName()])
        }
        catch (RuntimeException e){
           renderException(e)
        }
    }

    def delete() {
        EquipmentSlotType slotType = EquipmentSlotType.get(params.slotId)
        try{
            if(! slotType){
                throw new RuntimeException("error.object.not.found")
            }
            if(slotType.equipmentSlots?.size() > 0){
                throw new RuntimeException("error.used_in_creature")
            }
            if(slotType.requiredSlots?.size() > 0){
                throw new RuntimeException("error.used_in_itemType")
            }
            slotType.delete()
            render(text:message(code:'equipmentSlotType.deleted'))
        }
        catch (RuntimeException e){
            renderException e
        }
    }
        
}
