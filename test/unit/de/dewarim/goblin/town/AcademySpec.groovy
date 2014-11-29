package de.dewarim.goblin.town

import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Academy)
@Mock(Town)
class AcademySpec extends Specification {


    @Shared
    Town town

    void setup() {
        town = new Town(name: 'someTown', shortDescription: '-')
        town.save()
        def sampleAcademy = new Academy(name: 'akka', description: '-', town: town)
        sampleAcademy.save()
        mockForConstraintsTests(Academy, [
                sampleAcademy
        ])
    }


    void "create Academy"() {
        when:
        def academy = new Academy(name: name, description: description, town: town)

        then:
        academy.validate()

        where:
        name = "CatAcademy"
        description = "Academy for cats"
    }

    @Unroll("test academy constraints #field is #error")
    void "test constraints Academy"() {

        when:
        def academy = new Academy("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(academy, field, error)

        where:
        error      | field         | val
        'nullable' | 'name'        | null
        'nullable' | 'name'        | ' ' // dataBinder turns blank String to null.
        'nullable' | 'description' | null
        'nullable' | 'town'        | null

    }

    void "create duplicate Academy"() {
        when:
        // note: the constraint is (name,town), otherwise we could test this
        // in the 'test constraints' method via 'unique'
        def academy = new Academy(name: name, description: '-', town: town)
        academy.validate()

        then:
        academy.errors['name'] == 'unique'

        where:
        name = 'akka'

    }

}
