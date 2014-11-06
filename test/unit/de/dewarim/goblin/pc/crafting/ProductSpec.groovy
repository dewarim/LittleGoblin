package de.dewarim.goblin.pc.crafting

import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
@TestFor(Product)
class ProductSpec extends Specification {

    void setup() {
        def sampleProduct = new Product(name: 'Wand of Annihilation', 
                timeNeeded: 1, category: new ProductCategory(name: 'everything'))
        sampleProduct.save()
        mockForConstraintsTests(Product, [
                sampleProduct
        ])
    }


    @Unroll("check constraints #field is #error")
    void "test constraints of Product class"() {
        when:
        def product = new Product("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(product, field, error)

        where:
        error               | field        | val
        'nullable'          | 'name'       | null
        'nullable'          | 'name'       | ' '
        'valid'             | 'name'       | 'Wand of Wonders'
        'unique'            | 'name'       | 'Wand of Annihilation'
        'nullable'          | 'timeNeeded' | null
        'valid'             | 'timeNeeded' | 1
        'valid'             | 'timeNeeded' | 0
        'validator.invalid' | 'timeNeeded' | -1

    }

}
