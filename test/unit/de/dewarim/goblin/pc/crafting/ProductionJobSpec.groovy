package de.dewarim.goblin.pc.crafting

import de.dewarim.ConstraintUnitSpec
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.pc.PlayerCharacter
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
@TestFor(ProductionJob)
@Mock([PlayerCharacter, Product, ProductCategory, UserAccount])
class ProductionJobSpec extends Specification {

    void setup() {
      
    }

    @Unroll("create production job check constraint #field with #val is #error")
    void "create production job"() {
        when:
        def productionJob = new ProductionJob("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(productionJob, field, error)

        where:
        error      | field      | val
        'nullable' | 'pc'       | null
        'valid'    | 'pc'       | pc
        'nullable' | 'product'  | null
        'valid'    | 'product'  | product
        'nullable' | 'finished' | null
        'valid'    | 'finished' | new Date() + 1
        
    }
    
    PlayerCharacter getPc(){
        def user = new UserAccount(username:  "crafty", passwd: 'foodFood', userRealName: 'none')
        def playerCharacter = new PlayerCharacter(name:"Crafter-1", user: user)
        return playerCharacter
    }
    
    Product getProduct(){
        def productCategory = new ProductCategory(name: 'everything')
        Product product = new Product(name: "the crown of ages", timeNeeded: 1, category: productCategory)
        return product
    }
}
