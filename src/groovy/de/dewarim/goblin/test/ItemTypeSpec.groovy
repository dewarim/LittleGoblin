package de.dewarim.goblin.test

import de.dewarim.goblin.AttributeType
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.combat.WeaponAttribute
import de.dewarim.goblin.item.ItemAttribute
import de.dewarim.goblin.item.ItemCategory
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.ItemTypeFeature
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**

 */
class ItemTypeSpec extends Specification {

    CombatAttributeType resistFire = new CombatAttributeType(name: 'resist.fire',)
    CombatAttributeType resistCold = new CombatAttributeType(name: 'resist.cold',)
    CombatAttributeType coldDamage = new CombatAttributeType(name: 'cold.damage')

    void "getResistanceAttributes"() {
        given:
        ItemType shield = new ItemType(name: 'Shield of Nero the Unburnable')
        def resFire = new WeaponAttribute(itemType: shield, combatAttributeType: resistFire, subType: AttributeType.RESISTANCE)
        def resCold = new WeaponAttribute(itemType: shield, combatAttributeType: resistCold, subType: AttributeType.RESISTANCE)
        def damage = new WeaponAttribute(itemType: shield, combatAttributeType: coldDamage, subType: AttributeType.ATTACK)

        when:
        resistFire.save()
        resistCold.save()
        coldDamage.save()
        shield.save([flush: true])
        resFire.save()
        resCold.save()
        damage.save()

        then:
        shield.resistanceAttributes.size() == 2
        shield.combatAttributes.size() == 1
        shield.combatAttributes.find { it.combatAttributeType == coldDamage }
        shield.resistanceAttributes.find { it.combatAttributeType == resistFire }
        shield.resistanceAttributes.find { it.combatAttributeType == resistCold }
        
    }

}
