package de.dewarim.goblin.pc.skill

import de.dewarim.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
@TestFor(CombatSkill)
class CombatSkillSpec extends Specification {

    void setup() {
        def existingSkill = new CombatSkill(name: 'someSkill')
        existingSkill.save()
        mockForConstraintsTests(CombatSkill, [
                existingSkill
        ])
    }

    @Unroll("test CombatSkill constraints #field is #error")
    void "test constraints for CombatSkill"() {
        when:
        def skill = new CombatSkill("$field": val)

        then:
        ConstraintUnitSpec.validateConstraints(skill, field, error)

        where:
        error               | field        | val
        'nullable'          | 'name'       | null
        'nullable'          | 'name'       | ' ' // dataBinder turns blank String to null.
        'unique'            | 'name'       | 'someSkill'
        'nullable'          | 'initParams' | null
        'size.toobig'       | 'initParams' | getDummyString(5000) // too large
        'size.toosmall'     | 'initParams' | getDummyString(10) // too small
        'nullable'          | 'startLevel' | null
        'validator.invalid' | 'script'     | String.class
        'valid'             | 'script'     | DummySkillScript.class
        'valid'             | 'script'     | null
    }

    String getDummyString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int x = 0; x < length; x++) {
            sb.append('x')
        }
        return sb.toString();
    }

}
