package de.dewarim.goblin.test.crafting

import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.test.ConstraintUnitSpec
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
class ComponentSpec extends Specification {

    @Shared
    def prodCat = new ProductCategory(name: 'nails')
    
    @Unroll("check constraints #field is #error")
    void "test constraints of Component class"() {
        
        when:
        def component = new Component("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(component, field, error)

        where:
        error               | field      | val
        'nullable'          | 'itemType' | null
        'nullable'          | 'type'     | null
        'nullable'          | 'amount'   | null
        'nullable'          | 'product'  | null
        'valid'             | 'itemType' | new ItemType(name: 'Hammer of Belimar')
        'valid'             | 'type'     | ComponentType.INPUT
        'valid'             | 'product'  | new Product(name: 'nail of 9 inches', category: prodCat)
        'validator.invalid' | 'amount'   | -1
        'validator.invalid' | 'amount'   | 0
        'valid'             | 'amount'   | 1

    }

}
