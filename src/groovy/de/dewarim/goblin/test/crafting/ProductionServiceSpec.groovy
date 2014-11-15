package de.dewarim.goblin.test.crafting

import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.ProductionService
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemService
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.*
import grails.util.Holders
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification

/**
 * Unit test for ProductionService.
 */
class ProductionServiceSpec extends Specification {

    @Shared
    ProductionService productionService
    
    @Shared
    def user = new UserAccount(username: "crafty", passwd: 'foodFood', userRealName: 'none')
    @Shared
    def playerCharacter = new PlayerCharacter(name: "Crafter-1", user: user)
    @Shared
    def prodInputType = new ItemType(name: "gold nugget")
    @Shared
    def prodOutputType = new ItemType(name: "golden crown")
    @Shared
    def inputItems = new Item(prodInputType, playerCharacter)
    @Shared
    def prodCat = new ProductCategory(name: "head.wear")
    @Shared
    def crownProduct = new Product(name: "Crown Product", timeNeeded: 1, category: prodCat)
    @Shared
    def inputComponent = new Component(type: ComponentType.INPUT, itemType: prodInputType,
            product: crownProduct, amount: 2)
    @Shared
    def outputComponent = new Component(type: ComponentType.OUTPUT, itemType: prodOutputType,
            product: crownProduct, amount: 1)

    void setup() {
        
    }
    
    void cleanupSpec(){
        // delete any unfinished jobs.
        ProductionJob.list().each{it.deleteFully()}
    }
    
    void setupSpec(){
        ApplicationContext ctx = Holders.grailsApplication.mainContext
        productionService = ctx.getBean(ProductionService)
        
        user.save()
        playerCharacter.save()
        prodInputType.save()
        prodCat.save()
        crownProduct.save()
        prodOutputType.save()
        inputComponent.save()
        outputComponent.save()
    }
    
    void "test calculation of maximum products"() {
        given:
        inputItems.amount = 10
        inputItems.save()

        when:
        def maxProduction = productionService.computeMaxProduction(crownProduct, playerCharacter)

        then:
        maxProduction == 5
    }

    void "test fetch ItemMap"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        
        when:
        def itemMap = productionService.fetchItemMap(crownProduct, playerCharacter)

        then:
        itemMap != null
        itemMap.size() == 1
        itemMap.get(inputComponent).contains(inputItems)
        itemMap.get(inputComponent).size() == 1
    }

    void "extract item list from params"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        def params = ["item_${inputItems.id}": '5', "item_0": '100', "items_-1": '-1']

        when:
        def items = productionService.extractItemListFromParams(params)

        then:
        items.size() == 1
        items.contains(inputItems)
    }

    void "fetch itemCountMap from params"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        
        def params = ["item_${inputItems.id}": '5', "item_0": '100', "items_-1": '-1']

        when:
        def itemTypeToAmountMap = productionService.fetchItemCountMapFromParams(params)

        then:
        itemTypeToAmountMap.size() == 1
        itemTypeToAmountMap.get(prodInputType) == 5 // returns selected amount, not actual
    }

    void "enough resources selected"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        def validSelection = ["item_${inputItems.id}": '5']
        def invalidSelection = ["item_${inputItems.id}": '1']

        when:
        def hasEnough = productionService.enoughResourcesSelected(crownProduct, playerCharacter, validSelection)
        def notEnough = productionService.enoughResourcesSelected(crownProduct, playerCharacter, invalidSelection)

        then:
        hasEnough
        !notEnough
    }

    void "create new production job"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        def enough = ["item_${inputItems.id}": '5']
        def missing = ["item_${inputItems.id}": '1']

        when:
        def valid = productionService.createNewProductionJob(crownProduct, playerCharacter, enough)
        def invalid = productionService.createNewProductionJob(crownProduct, playerCharacter, missing)

        then:
        valid.result.isPresent()
        valid.result.get() != null
        valid.result.get() != Optional.empty()
        !invalid.result.present
        invalid.errors.find { it.equals('production.missing.resources') }
    }

    void "detect new production job with stolen items"() {
        given:
        inputItems.amount = 10
        def thief = new PlayerCharacter(name: "Crafter-2", user: user)
        thief.save()
        inputItems.owner = thief
        inputItems.save()
        def enough = ["item_${inputItems.id}": '5']
        def missing = ["item_${inputItems.id}": '1']

        when:
        def validButStolen = productionService.createNewProductionJob(crownProduct, playerCharacter, enough)
        def invalidAndStolen = productionService.createNewProductionJob(crownProduct, playerCharacter, missing)

        then:
        !validButStolen.result.present
        !invalidAndStolen.result.present

        invalidAndStolen.errors.find { it.equals('production.missing.resources') }
        validButStolen.errors.find { it.equals('production.foreign.item') }
    }
    
    void "make products"() {
        given:
        inputItems.amount = 10
        inputItems.owner = playerCharacter
        inputItems.save()
        def enough = ["item_${inputItems.id}": '5']

        when:
        def jobCount = ProductionJob.count
        def resourceCount = ProductionResource.count
        def oldItemCount = Item.count
        def optionalJob = productionService.createNewProductionJob(crownProduct, playerCharacter, enough)
        def job = optionalJob.result.get()
        def productCount = productionService.makeProducts([job])

        then:
        ProductionJob.list().size() == jobCount // the new job should have been deleted after makeProducts
        productCount == 1
        Component.findByItemType(prodOutputType)
        playerCharacter.items.find {
            it.type.equals(prodOutputType) && it.type.name.equals("golden crown")
        }
        ProductionResource.count() == resourceCount
        Item.count() == oldItemCount+1
        playerCharacter.items.find {
            it.type.equals(prodInputType) && it.amount.equals(8)
        }
    }

}
