package de.dewarim.goblin;
import grails.plugins.springsecurity.Secured

import de.dewarim.goblin.shop.Shop

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.Item

class ShopController extends BaseController {

    def itemService
    def shopService

    @Secured(['ROLE_USER'])
    def show = {
        def pc = fetchPc(session)
        session.filters = [] // start with showing all items.
        Shop shop = Shop.get(params.shop)
        if (!pc?.town?.shops?.find {it.equals(shop)}) {
            /*
            *  It has to be a shop in the player characters town,
            *  or something is wrong.
            */
            return redirect(controller: 'town', action: 'show', params: [pc: params.pc])
        }
        if (!shop) {
            flash.message = message(code: 'error.shop.not_found')
            return redirect(controller: 'town', action: 'show', params: [pc: params.pc])
        }
        if (shop.itemTypes?.size() == 0) {
            fetchWares(shop)
        }
//        def itemCategoryMap = itemService.fetchItemCategoryTypeMap(shop.itemTypes)
        def categories = shopService.fetchCategoryList(shop).sort {message(code: it.name)}
        return [shop: shop,
                pc: pc,
                shopping: true,
                shopItems: shop.itemTypes.sort {message(code: it.name)},
                categories: categories
        ]
    }

    @Secured(['ROLE_USER'])
    def addCategory = {
        def pc = fetchPc(session)
        try {
            Category cat = (Category) inputValidationService.checkObject(Category.class, params.category)
            Shop shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)
            def filters = session.categoryFilters
            if (!filters) {
                filters = [cat]
                session.categoryFilters = filters
            }
            else {
                filters.add(cat)
            }
            def shopItems = itemService.filterItemTypesByCategory(shop.itemTypes, filters)

            render(template: 'itemList', model: [pc: pc,
                    shop: shop,
                    shopItems: shopItems.sort {message(code: it.name)}
            ])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    @Secured(['ROLE_USER'])
    def removeCategory = {
        def pc = fetchPc(session)
        try {
            Category cat = (Category) inputValidationService.checkObject(Category.class, params.category)
            Shop shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)
            def filters = session.categoryFilters
            if (!filters) {
                filters = []
                session.categoryFilters = filters
            }
            else {
                filters.remove(cat)
            }
            def shopItems = itemService.filterItemTypesByCategory(shop.itemTypes, filters)

            render(template: 'itemList', model: [pc: pc,
                    shop: shop,
                    shopItems: shopItems.sort {message(code: it.name)}
            ])
        }
        catch (RuntimeException e) {
            log.debug(e)
            renderException e
        }
    }

    @Secured(['ROLE_USER'])
    def reloadCategories = {
        def pc = fetchPc(session)
        try {
            Shop shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)
            def itemCategoryMap = itemService.fetchItemCategoryTypeMap(shop.itemTypes)
            render(template: 'categoryFilter', model: [shop: shop,
                    pc: pc,
                    categories: shopService.fetchCategoryList(shop).sort {message(code: it.name)}])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    @Secured(['ROLE_USER'])
    def showAllCategories = {
        def pc = fetchPc(session)
        try {
            Shop shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)
            session.categoryFilters = []

            render(template: 'itemList', model: [pc: pc,
                    shop: shop,
                    shopItems: shop.itemTypes.sort {message(code: it.name)}
            ])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }


    void fetchWares(Shop shop) {
        shop.itemTypes = itemService.fetchItemTypes(shop)
    }

    @Secured(['ROLE_USER'])
    def buy = {
        def pc = fetchPc(session)
        Shop shop = Shop.get(params.shop)
        log.debug("shop: $shop")
        log.debug("player town: ${pc.town}")
        if (!pc?.town?.shops?.find {it.equals(shop)}) {
            /*
            *  It has to be a shop in the player characters town,
            *  or something is wrong.
            */
            return render(status: 503, text: message(code: 'error.wrong_shop'))
        }
        def itemType = ItemType.get(params.itemType)
        Integer amount = params.amount ? inputValidationService.checkAndEncodeInteger(params, 'amount', 'amount') : 1
        if (shop.itemTypes.find {it.id.equals(itemType.id) }) {
            Integer price = shop.owner.calculatePrice(itemType)
            Integer totalCost = price * amount
            if (pc.gold >= totalCost) {
                pc.gold = pc.gold - totalCost
                // check if the player already has one item of this type
                Item existingItem = pc.items.find {
                        it.type == itemType
                }
                if (itemType.stackable && existingItem) {
                    existingItem.amount += amount * itemType.packageSize
                }
                else {
                    Item item = new Item(type: itemType, owner: pc)
                    item.initItem(amount * itemType.packageSize)
                    item.save()
                }
                return render(template: '/shared/sideInventory', model: [pc: pc, shop: shop])
            }
            else {
                return render(status: 503, text: message(code: 'error.insufficient.gold'))
            }
        }
        else {
            return render(status: 503, text: message(code: 'error.item.not_found'))
        }

    }

    @Secured(['ROLE_USER'])
    def sell = {
        try {
            def pc = fetchPc(session)
            Shop shop = (Shop) inputValidationService.checkObject(Shop.class, params.shop)

            if (!pc?.town?.shops?.find {it.equals(shop)}) {
                /*
                *  It has to be a shop in the player characters town,
                *  or something is wrong.
                */
                throw new RuntimeException('error.wrong_shop')
            }
            def item = fetchItem(pc)

            def amount = Math.abs(inputValidationService.checkAndEncodeInteger(params, "amount", 'item.amount'))
            amount = amount > item.amount ? item.amount : amount
            def sellPrice = (shop.owner.calculateSellPrice(item))
            pc.gold = pc.gold + sellPrice * amount
            if (item.amount > amount) {
                log.debug("splitting of the sold amount of item")
                item = itemService.splitItem(item, amount)
            }
            else {
                item.amount -= amount
            }
            item.delete()
            return render(template: '/shared/sideInventory', model: [pc: pc, shop: shop])
        }
        catch (Exception e) {
            log.debug("failed to sell item: ", e)
            return render(status: 503, text: message(code: e.message))
        }


    }


}
