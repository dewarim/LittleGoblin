package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Unroll

/**
 */
@TestFor(ProductionJob)
@Mock([PlayerCharacter, Product])
class ProductionJobSpec extends ConstraintUnitSpec {

    void setup() {
        
    }

    @Unroll("create production job check constraint #field is #error")
    void "create production job"() {
        when:
        def productionJob = new ProductionJob("$field": val)

        then:
        validateConstraints(productionJob, field, error)

        where:
        error      | field      | val
        'nullable' | 'pc'       | null
        'valid'    | 'pc'       | playerCharacter
        'nullable' | 'product'  | null
        'valid'    | 'product'  | product
        'nullable' | 'finished' | null
        'valid'    | 'finished' | new Date() + 1
        
    }
}
