package de.dewarim.goblin.test.crafting

import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.test.ConstraintUnitSpec
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
class ProductSpec extends Specification {

    @Shared
    def category = new ProductCategory(name: Math.random().toString())

    @Shared
    def sampleProduct = new Product(name: 'Wand of Annihilation',
            timeNeeded: 1, category: category)

    void setupSpec() {
        category.save()
        sampleProduct.save()
    }

    @Unroll("check constraints #field is #error")
    void "test constraints of Product class"() {
        when:
        def product = new Product("$field": val, category: category)

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
