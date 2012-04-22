package de.dewarim.goblin.combat

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.ICombatAttribute

/**
 * Mapping between ItemType and CombatAttributeType.
 * An Item may cause special types of damage (for example, a poisonous blade will
 * cause extra poison damage), and in that case a WeaponAttribute will
 * be added to the ItemType to specify the amount of damage caused.
 * Expansion possibility: also allow individual WeaponAttributes on crafted or enchanted items.
 */
class WeaponAttribute implements ICombatAttribute{

    static belongsTo = [itemType:ItemType, combatAttributeType:CombatAttributeType]

    Double damageModifier = 1.0

    CombatAttributeType fetchCombatAttributeType(){
        return combatAttributeType
    }

    Double fetchDamageModifier(){
        return damageModifier
    }

}
