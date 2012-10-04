package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.shop.Shop
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.shop.ShopOwner
import de.dewarim.goblin.town.Town

@Secured(["ROLE_ADMIN"])
class ShopAdminController extends BaseController {

    def index() {
        return [
                shops: Shop.listOrderByName()
        ]
    }

    def edit() {
        def shop = Shop.get(params.id)
        if (!shop) {
            return render(status: 503, text: message(code: 'error.unknown.shop'))
        }
        render(template: 'edit', model: [shop: shop])
        return
    }

    def cancelEdit() {
        def shop = Shop.get(params.id)
        if (!shop) {
            return render(status: 503, text: message(code: 'error.unknown.shop'))
        }
        render(template: 'update', model: [shop: shop])
        return
    }

    def update() {
        try {
            def shop = Shop.get(params.id)
            if (!shop) {
                throw new RuntimeException('error.unknown.shop')
            }
            updateFields shop
            shop.save()
            render(template: 'list', model: [shops: Shop.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(shop) {
        shop.name = inputValidationService.checkAndEncodeName(params.name, shop)
        if(params.description){
            shop.description =
                inputValidationService.checkAndEncodeText(params, "description", "shop.description")
        }
        else{
            shop.description = null
        }
        shop.owner = inputValidationService.checkObject(ShopOwner.class, params.owner)
        shop.town = inputValidationService.checkObject(Town.class, params.town)
    }

    def save() {
        Shop shop = new Shop()
        try {
            updateFields(shop)
            shop.save()
            render(template: 'list', model: [shops: Shop.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Shop shop = Shop.get(params.id)
        try {
            if (!shop) {
                throw new RuntimeException("error.object.not.found")
            }
            shop.delete()
            render(text: message(code: 'shop.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
