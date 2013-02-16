package de.dewarim.goblin.combat

import de.dewarim.goblin.ICombatAttribute
import de.dewarim.goblin.item.ItemType

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

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof WeaponAttribute)) return false

        WeaponAttribute that = o

        if (combatAttributeType != that.combatAttributeType) return false
        if (damageModifier != that.damageModifier) return false
        if (itemType != that.itemType) return false

        return true
    }

    int hashCode() {
        int result
        result = (damageModifier != null ? damageModifier.hashCode() : 0)
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0)
        result = 31 * result + (combatAttributeType != null ? combatAttributeType.hashCode() : 0)
        return result
    }
}
