package de.dewarim.goblin;


import grails.plugins.springsecurity.Secured

import de.dewarim.goblin.item.Item;


import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.shop.Shop

class ItemController extends BaseController {

    def featureService
    def itemService

    /**
     * Use an item belonging to the player in the context of a combat setting.
     */
    @Secured(['ROLE_USER'])
    def useItem() {
        def user = fetchUser()

        def item = Item.get(params.item)
        def combat = Combat.get(params.combat)

        // check input parameters
        if (!combat) {
            flash.message = message(code: 'error.combat_not_found')
            return redirect(action: 'show', controller: 'town')
        }
        if (!combat.playerCharacter.user.equals(user)) {
            flash.message = message(code: 'error.wrong_combat')
            return redirect(action: 'show', controller: 'town')
        }
        def pc = combat.playerCharacter

        def itemFeature = Feature.get(params.feature)
        def itemType = item.type

        if (!pc.equals(item.owner)) {
            // tried to use an item that does not belong to him.
            flash.message = message(code: "error.item_not_found")
            return redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
        }
        if (!itemType.itemTypeFeatures?.find {it.feature.id == itemFeature.id}) {
            // tried to use an unmapped feature.
            flash.message = message(code: "error.feature_not_found")
            return redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
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
//			return redirect(action:'fight', controller:'fight', params:[combat:combat.id])
        }
        return redirect(action: 'fight', controller: 'fight', params: [combat: combat.id])
    }

    @Secured(['ROLE_USER'])
    def equipItem() {
        def pc = fetchPc()
        try {
            if (!pc) {
                return render(status: 503, text: message(code: 'error.no.pc'))
            }

            Item item = Item.get(params.item)
            if (!item) {
                return render(status: 503, text: message(code: 'error.item.not_found'))
            }
            if (!pc.equals(item.owner)) {
                return render(status: 503, text: message(code: 'error.wrong_owner'))
            }
            if (!(item.type.requiredSlots?.size() > 0)) {
                return render(status: 503, text: message(code: 'error.item.unequippable'))
            }
            if (item.equipped) {
                return render(status: 503, text: message(code: 'error.item.is_equipped'))
            }
            log.debug("Trying to equip item: ${item.type.name}")
            if (!pc.equipItem(item)) {
                return render(status: 503, text: message(code: 'error.slots.full'))
            }
            if (params.shop) {
                log.debug("found shop: ${params.shop}")
                def shop = inputValidationService.checkObject(Shop.class, params.shop)
                return render(template: '/shared/equipment', model:[pc:pc, shop:shop])
            }
            return render(template: 'inventory', model: [pc: pc])
        }
        catch (Exception e) {
            return render(status: 500, text:message(code:'error.equip.fail', args:[message(code:e.message)]))
        }

    }

    @Secured(['ROLE_USER'])
    def unequipItem() {
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
            if (params.shop) {
                shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)
            }
            if(params.sideInventory){
                // render left side inventory instead of main inventory
                return render(template: '/shared/sideInventory', model: [pc: pc, shop: shop])
            }
            else{
                return render(template: 'inventory', model: [pc: pc, shop: shop])
            }
        }
        catch (Exception e) {
            log.debug("failed to unequip item because of:", e)
            return render(status: 503, text: message(code: e.getMessage()))
        }
    }


    @Secured(['ROLE_USER'])
    def showInventory() {
        // TODO: if pc is on a quest or in combat, prevent him from moving stuff from home to person.
        def pc = fetchPc()
        if (!pc) {
            return redirect(controller: 'portal', action: 'start')
        }
        return [pc: pc]
    }

    @Secured(['ROLE_USER'])
    def renderInventory() {
        def pc = fetchPc()
//        render tem
    }

    /*
     * This item system is rather simple: it expects one player owning one home to carry around his belongings.
     * To simulate a fleet of space ships, each carrying items and storing them at different planets, this system
     * would need to be considerably expanded.
     */
    @Secured(['ROLE_USER'])
    def carryItem() {
        try {
            def pc = fetchPc()
            def item = fetchItem(pc)
            if (item.location == ItemLocation.ON_PERSON) {
                // nothing to do.
                log.debug("item to carry is already 'on person'.")
                return render(template: 'inventory', model: [pc: pc])
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
            return render(template: 'inventory', model: [pc: pc])
        }
        catch (Exception e) {
            log.debug("failed to carry item: ", e)
            return render(status: 503, text: message(code: e.message))
        }
    }

    @Secured(['ROLE_USER'])
    def dropItem() {
        try {
            def pc = fetchPc()
            def item = fetchItem(pc)
            if (item.location == ItemLocation.AT_HOME) {
                // nothing to do.
                log.debug("item to drop is already 'at_home'")
                return render(template: 'inventory', model: [pc: pc])
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

            return render(template: 'inventory', model: [pc: pc])
        }
        catch (Exception e) {
            log.debug("failed to drop item: ", e)
            return render(status: 503, text: message(code: e.message))
        }
    }

}
