package de.dewarim.goblin.town

import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Academy)
@Mock(Town)
class AcademySpec extends ConstraintUnitSpec {


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
        validateConstraints(academy, field, error)

        where:
        error      | field         | val
        'nullable' | 'name'        | null
        'nullable' | 'name'        | ' ' // dataBinder turns blank String to null.
        'nullable' | 'description' | null
        'nullable' | 'town'        | null

    }

    void "create duplicate Academy"() {
        when:
        def academy = new Academy(name: name, description: '-', town: town)
        academy.validate()

        then:
        academy.errors['name'] == 'unique'     
            
        where:
        name = 'akka'

    }

}
