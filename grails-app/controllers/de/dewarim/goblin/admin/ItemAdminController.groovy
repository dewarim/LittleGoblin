package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.item.ItemCategory
import de.dewarim.goblin.item.ItemType
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Dice
import de.dewarim.goblin.EquipmentSlotType
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.Category

@Secured(["ROLE_ADMIN"])
class ItemAdminController extends BaseController {

    def index() {
        return [
                itemTypes: ItemType.listOrderByName(),
                itemCategoryIdList: []
        ]
    }

    def edit() {
        def itemType = ItemType.get(params.id)
        if (!itemType) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        def itemCategoryIdList = ItemCategory.findAllByItemType(itemType).collect { it.category.id }
        render(template: 'edit', model: [itemType: itemType, itemCategoryIdList: itemCategoryIdList])
    }

    def cancelEdit() {
        def itemType = ItemType.get(params.id)
        if (!itemType) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: 'update', model: [itemType: itemType])
    }

    def update() {
        try {
            def itemType = ItemType.get(params.id)
            if (!itemType) {
                throw new RuntimeException('error.object.not.found')
            }
            updateFields itemType
            itemType.save()
            render(template: 'list', model: [itemTypes: ItemType.listOrderByName()])
        }
        catch (RuntimeException e) {
            log.debug("failed to update itemType:",e)
            renderException e
        }
    }

    protected void updateFields(ItemType itemType) {
        itemType.name = inputValidationService.checkAndEncodeName(params.name, itemType)
        itemType.description =
            inputValidationService.checkAndEncodeText(params, "description", "itemType.description")
        itemType.uses = inputValidationService.checkAndEncodeInteger(params, "uses", "itemType.level")
        itemType.baseValue = inputValidationService.checkAndEncodeInteger(params, "baseValue", "itemType.baseValue")
        itemType.availability = inputValidationService.checkAndEncodeInteger(params, "availability", "itemType.availability")
        itemType.packageSize = inputValidationService.checkAndEncodeInteger(params, "packageSize", "itemType.packageSize")
        itemType.usable = inputValidationService.checkAndEncodeBoolean(params, "usable", "itemType.usable")
        itemType.stackable = inputValidationService.checkAndEncodeBoolean(params, "stackable", "itemType.stackable")
        itemType.rechargeable = inputValidationService.checkAndEncodeBoolean(params, "rechargeable", "itemType.rechargeable")
        if (params.combatDice){
            itemType.combatDice = inputValidationService.checkObject(Dice.class, params.combatDice, true)
        }

        def categories = params.list('category').collect{inputValidationService.checkObject(Category.class, it)}
        itemType.itemCategories.collect{it}.each{ itemCategory ->
            if(! categories.contains(itemCategory.category)){
                itemCategory.deleteFully()
            }
        }
        categories.each{Category category ->
            if (! itemType.itemCategories.find{it.category.equals(category)}){
                ItemCategory ic = new ItemCategory(itemType:itemType, category:category)
                ic.save()
            }
        }
    }

    def save() {
        ItemType itemType = new ItemType()
        try {
            updateFields(itemType)
            itemType.save()
            render(template: 'list', model: [itemTypes: ItemType.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        ItemType itemType = ItemType.get(params.id)
        try {
            if (!itemType) {
                throw new RuntimeException("error.object.not.found")
            }
            if (itemType.items.size() > 0) {
                throw new RuntimeException("error.itemType.in.use")
            }
            itemType.delete()
            render(text: message(code: 'object.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    def updateRequiredSlots() {
        try {
            ItemType itemType = inputValidationService.checkObject(ItemType.class, params.id)
            def slots = params.keySet().findAll { key ->
                key.startsWith('slotType_')
            }
            def slotMap = [:]
            slots.each { slotTypeId ->
                def slot = inputValidationService.checkObject(EquipmentSlotType.class, slotTypeId.split('_')[1])
                slotMap.put(slot, inputValidationService.checkAndEncodeInteger(params, "$slotTypeId", "equipmentSlotType.id"))

                // check if this slot already exists:
                RequiredSlot requiredSlot = RequiredSlot.findWhere(slotType: slot, itemType: itemType)
                if (requiredSlot) {
                    if (slotMap.get(slot) == 0) {
                        // slot is not longer used:
                        requiredSlot.delete()
                    }
                    else {
                        requiredSlot.amount = slotMap.get(slot)
                    }
                }
                else {
                    RequiredSlot newSlot = new RequiredSlot(slotType: slot, itemType: itemType, amount: slotMap.get(slot))
                    newSlot.save()
                }
            }

            render(template: 'requiredSlots', model: [itemType: itemType, updated: true])
        }
        catch (RuntimeException e) {
            log.debug("updateRequiredSlots:", e)
            renderException(e)
        }
    }

}
