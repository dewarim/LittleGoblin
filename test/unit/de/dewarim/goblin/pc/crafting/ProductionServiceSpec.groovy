package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.ProductionService
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemService
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit test for ProductionService.
 */
@TestFor(ProductionService)
@Mock([PlayerCharacter, Item, ItemType, Component, Product, UserAccount, ProductCategory])
class ProductionServiceSpec extends Specification {

    def productionService = new ProductionService()
    def itemService = new ItemService()

    @Shared
    def user = new UserAccount(username: "crafty", passwd: 'foodFood', userRealName: 'none')
    @Shared
    def playerCharacter = new PlayerCharacter(name: "Crafter-1", user: user)
    @Shared
    def prodInputType = new ItemType(name: "gold nugget")

    @Shared
    def inputItems = new Item(prodInputType, playerCharacter)

    @Shared
    def prodCat = new ProductCategory(name: "head.wear")

    @Shared
    def crownProduct = new Product(name: "Crown Product", timeNeeded: 1, category: prodCat)

    @Shared
    def inputComponent = new Component(type: ComponentType.INPUT, itemType: prodInputType,
            product: crownProduct, amount: 2)

    void setup() {
        productionService.itemService = itemService
    }

    void "test calculation of maximum products"() {
        /*
         * No need to save all objects, as many are saved by cascade.
         * If you write more complex tests, it may be necessary to save more @Shared
         * fields before using GORM methods on them.
         */
        given:
        prodInputType.save()
        inputItems.amount = 10
        inputItems.save(failOnError: true)
        prodCat.save()
        crownProduct.save(failOnError: true)
        inputComponent.save(failOnError: true)

        when:
        def allItems = Item.findAll()
        def items = playerCharacter.items
        def maxProduction = productionService.computeMaxProduction(crownProduct, playerCharacter)

        then:
        allItems.size() == 1
        items.size() == 1
        maxProduction == 5
    }

    void "test fetch ItemMap"() {
        given:
        playerCharacter.save()
        inputItems.amount = 10
        inputItems.save()
        crownProduct.save()
        inputComponent.save()

        when:
        def itemMap = productionService.fetchItemMap(crownProduct, playerCharacter)

        then:
        itemMap != null
        itemMap.size() == 1
        itemMap.get(inputComponent).contains(inputItems)
        itemMap.get(inputComponent).size() == 1
    }

}
