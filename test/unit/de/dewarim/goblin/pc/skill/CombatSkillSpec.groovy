package de.dewarim.goblin.pc.skill

import de.dewarim.ConstraintUnitSpec
import de.dewarim.goblin.ISkillScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.ProductionJob
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.Town
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Unroll

/**
 */
@TestFor(CombatSkill)
class CombatSkillSpec extends ConstraintUnitSpec {

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
        validateConstraints(skill, field, error)

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
