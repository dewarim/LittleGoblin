package de.dewarim.goblin.test.skill

import de.dewarim.goblin.pc.skill.CombatSkill
import de.dewarim.goblin.pc.skill.DummySkillScript
import de.dewarim.goblin.test.ConstraintUnitSpec
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 */
class CombatSkillSpec extends Specification {

    @Shared
    def existingSkill = new CombatSkill(name: 'someSkill')
    
    void setup() {
        existingSkill.save()
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
