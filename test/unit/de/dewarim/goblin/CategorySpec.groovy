package de.dewarim.goblin

import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
@TestFor(Category)
class CategorySpec extends Specification {

    void setup() {
        def sampleCat = new Category(name: 'beasts')
        sampleCat.save()
        mockForConstraintsTests(Category, [
                sampleCat
        ])
    }


    @Unroll("check constraints #field is #error")
    void "test constraints of Category class"() {

        when:
        def category = new Category("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(category, field, error)

        where:
        error      | field  | val
        'nullable' | 'name' | null
        'valid'    | 'name' | 'cattle'
        'unique'   | 'name' | 'beasts'

    }

}
