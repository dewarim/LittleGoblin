package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.shop.Shop

@Secured(['ROLE_USER'])
class ItemController extends BaseController {

    def featureService
    def itemService

    /**
     * Use an item belonging to the player in the context of a combat setting.
     */
    def useItem() {
        def user = fetchUser()

        def item = Item.get(params.item)
        def combat = Combat.get(params.combat)

        // check input parameters
        if (!combat) {
            flash.message = message(code: 'error.combat_not_found')
            redirect(action: 'show', controller: 'town')
            return
        }
        if (!combat.playerCharacter.user.equals(user)) {
            flash.message = message(code: 'error.wrong_combat')
            redirect(action: 'show', controller: 'town')
            return
        }
        def pc = combat.playerCharacter

        def itemFeature = Feature.get(params.feature)
        def itemType = item.type

        if (!pc.equals(item.owner)) {
            // tried to use an item that does not belong to him.
            flash.message = message(code: "error.item_not_found")
            redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
            return
        }
        if (!itemType.itemTypeFeatures?.find {it.feature.id == itemFeature.id}) {
            // tried to use an unmapped feature.
            flash.message = message(code: "error.feature_not_found")
            redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
            return
        }

        // Is the item usable? Then execute its script.
        if (itemType.usable && item.uses > 0) {
            // TODO: convert to use MeleeAction with featureConfig.
            featureService.executeFeature(itemFeature, '<config />',  pc, combat.mobs.collect {(Creature) it}, item)
            item.uses = item.uses - 1
            if (item.uses == 0) {
                if (!itemType.rechargeable) {
//                    itemType.removeFromItems(item)
                    //                    pc.removeFromItems(item)
                    item.delete()
                }
            }
        }
        else {
            flash.message = message(code: 'error.no_use_left')
//          redirect(action:'fight', controller:'fight', params:[combat:combat.id])
//          return
        }
        redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
    }

    def equipItem(Integer shopId) {
        def pc = fetchPc()
        try {
            if (!pc) {
                render(status: 503, text: message(code: 'error.no.pc'))
                return
            }

            Item item = Item.get(params.item)
            if (!item) {
                render(status: 503, text: message(code: 'error.item.not_found'))
                return
            }
            if (!pc.equals(item.owner)) {
                render(status: 503, text: message(code: 'error.wrong_owner'))
                return
            }
            if (!(item.type.requiredSlots?.size() > 0)) {
                render(status: 503, text: message(code: 'error.item.unequippable'))
                return
            }
            if (item.equipped) {
                render(status: 503, text: message(code: 'error.item.is_equipped'))
                return
            }
            log.debug("Trying to equip item: ${item.type.name}")
            if (!pc.equipItem(item)) {
                render(status: 503, text: message(code: 'error.slots.full'))
                return
            }
          
            if (shopId) {
                log.debug("found shop: ${shopId}")
                def shop = inputValidationService.checkObject(Shop.class, params.shopId)
                render(template: '/shared/equipment', model:[pc:pc, shop: shop])
                return
            }
            if(params.sideInventory){
                // render left side inventory instead of main inventory
                render(template: '/shared/equipment', model: [pc: pc])
            }
            else{
                render(template: 'inventory', model: [pc: pc])
            }
        }
        catch (Exception e) {
            renderException(e, 'error.equip.fail')
        }

    }

    def unequipItem(Integer shopId) {
        try {
            def pc = fetchPc()
            Item item = (Item) inputValidationService.checkObject(Item.class, params.item)
            if (!pc.equals(item.owner)) {
                throw new RuntimeException('error.wrong_owner')
            }
            if (!item.equipped) {
                throw new RuntimeException('error.item.is_not_equipped')
            }
            pc.unequipItem(item)
            log.debug("params.shop:" + params.shop)
            Shop shop = null
            if (shopId) {
                shop = (Shop) inputValidationService.checkObject(Shop.class, params.shopId)
            }
            if(params.sideInventory){
                // render left side inventory instead of main inventory
                render(template: '/shared/sideInventory', model: [pc: pc, shop: shop, items: pc.items])
            }
            else{
                render(template: 'inventory', model: [pc: pc, shop: shop])
            }
        }
        catch (Exception e) {
            log.debug("failed to unequip item because of:", e)
            render(status: 503, text: message(code: e.getMessage()))
        }
    }

    def showInventory() {
        // TODO: if pc is on a quest or in combat, prevent him from moving stuff from home to person.
        def pc = fetchPc()
        if (!pc) {
            redirect(controller: 'portal', action: 'start')
            return
        }
        return [pc: pc]
    }

    def renderInventory() {
        def pc = fetchPc()
//        render tem
    }

    /*
     * This item system is rather simple: it expects one player owning one home to carry around his belongings.
     * To simulate a fleet of space ships, each carrying items and storing them at different planets, this system
     * would need to be considerably expanded.
     */
    def carryItem() {
        try {
            def pc = fetchPc()
            def item = fetchItem(pc)
            if (item.location == ItemLocation.ON_PERSON) {
                // nothing to do.
                log.debug("item to carry is already 'on person'.")
                render(template: 'inventory', model: [pc: pc])
                return
            }

            def amount = Math.abs(inputValidationService.checkAndEncodeInteger(params, "amount", 'item.amount'))
            amount = amount > item.amount ? item.amount : amount

            if (item.type.stackable) {
                def itemOnPerson = pc.items.find {(it != item) && (it.type == item.type) && (it.location == ItemLocation.ON_PERSON)}
                if (itemOnPerson) {
                    itemOnPerson.amount += amount
                    item.amount -= amount
                    if (item.amount == 0) {
                        item.delete()
                    }
                }
                else {
                    if (amount < item.amount) {
                        log.debug("splitting item")
                        item = itemService.splitItem(item, amount)
                    }
                    item.location = ItemLocation.ON_PERSON
                }
            }
            else {
                item.location = ItemLocation.ON_PERSON
            }
            render(template: 'inventory', model: [pc: pc])
        }
        catch (Exception e) {
            log.debug("failed to carry item: ", e)
            render(status: 503, text: message(code: e.message))
        }
    }

    def dropItem() {
        try {
            def pc = fetchPc()
            def item = fetchItem(pc)
            if (item.location == ItemLocation.AT_HOME) {
                // nothing to do.
                log.debug("item to drop is already 'at_home'")
                render(template: 'inventory', model: [pc: pc])
                return
            }
            def amount = Math.abs(inputValidationService.checkAndEncodeInteger(params, "amount", 'item.amount'))
            amount = amount > item.amount ? item.amount : amount
            if (item.type.stackable) {
                def itemAtHome = pc.items.find {(it != item) && (it.type == item.type) && (it.location == ItemLocation.AT_HOME)}
                if (itemAtHome) {
                    itemAtHome.amount += amount
                    item.amount -= amount
                    if (item.amount == 0) {
                        item.delete()
                    }
                }
                else {
                    if (amount < item.amount) {
                        log.debug("splitting item")
                        item = itemService.splitItem(item, amount)
                    }
                    item.location = ItemLocation.AT_HOME
                }
            }
            else {
                item.location = ItemLocation.AT_HOME
            }

            render(template: 'inventory', model: [pc: pc])
        }
        catch (Exception e) {
            log.debug("failed to drop item: ", e)
            render(status: 503, text: message(code: e.message))
        }
    }
}
