package de.dewarim.goblin.pc.crafting

import de.dewarim.ConstraintUnitSpec
import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.ProductionService
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemService
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * Unit test for ProductionService.
 */
@TestFor(ProductionService)
@Mock([Component, Item, ItemType, Product, PlayerCharacter])
class ProductionServiceSpec extends ConstraintUnitSpec {

    def productionService = new ProductionService()
    def itemService = new ItemService()
    def pc = playerCharacter
    def prodInput = getItemType("gold nugget")
    def prodToolType = getItemType("hammer")
    def prodOutput = getItemType("golden crown")

    void setup() {
        productionService.itemService = itemService
    }

    void "test calculation of maximum products"() {
        def inputItems = new Item(prodInput, pc)
        inputItems.amount = 10
        inputItems.save()
        def prodTool = new Item(prodToolType, pc)
        prodTool.save()
        def crownProduct = new Product(name: "Crown Product")
        def inputComponent = new Component(type: ComponentType.INPUT, itemType: prodInput,
                product: crownProduct, amount: 2)
        inputComponent.save()
        def toolComponent = new Component(type: ComponentType.TOOL, itemType: prodToolType,
                product: crownProduct, amount: 1)
        toolComponent.save()
        
        when:
        def maxProduction = productionService.computeMaxProduction(crownProduct, pc)

        then:
        maxProduction == 5
    }

}
