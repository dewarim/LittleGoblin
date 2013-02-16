package de.dewarim.goblin.admin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Dice
import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.shop.ShopOwner

@Secured(["ROLE_ADMIN"])
class ShopOwnerAdminController extends BaseController {

    def index() {
        return [
                shopOwners: ShopOwner.listOrderByName()
        ]
    }

    def edit() {
        def shopOwner = ShopOwner.get(params.id)
        if (!shopOwner) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/shopOwnerAdmin/edit', model: [shopOwner: shopOwner])
    }

    def cancelEdit() {
        def shopOwner = ShopOwner.get(params.id)
        if (!shopOwner) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/shopOwnerAdmin/update', model: [shopOwner: shopOwner])
    }

    def update() {
        try {
            def shopOwner = ShopOwner.get(params.id)
            if (!shopOwner) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${shopOwner.name}")
            updateFields shopOwner
            shopOwner.save()
            render(template: '/shopOwnerAdmin/list', model: [shopOwners:ShopOwner.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update shopOwner:",e)
            renderException e
        }
    }

    protected void updateFields(shopOwner){
        shopOwner.name = inputValidationService.checkAndEncodeName(params.name, shopOwner)
        shopOwner.description =
            inputValidationService.checkAndEncodeText(params, "description", "shopOwner.description")
        shopOwner.priceModifierDice =
            inputValidationService.checkObject(Dice.class, params.priceModifierDice)
        def shops = params.list("shops").collect {inputValidationService.checkObject(Shop.class, it)}
        shops.each{Shop shop ->
            shop.owner.removeFromShops shop
            shop.owner = shopOwner
            shopOwner.addToShops shop
        }
    }

    def save() {
        ShopOwner shopOwner = new ShopOwner()
        try {
           updateFields(shopOwner)
           shopOwner.save()

           render(template: '/shopOwnerAdmin/list', model: [shopOwners: ShopOwner.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save shopOwner", e
            renderException(e)
        }
    }

    def delete() {
        ShopOwner shopOwner = ShopOwner.get(params.id)
        try {
            if (!shopOwner) {
                throw new RuntimeException("error.object.not.found")
            }
            if( shopOwner.homes){
                throw new RuntimeException("error.shopOwner.inhabited")
            }
            shopOwner.delete()
            render(text: message(code: 'object.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
