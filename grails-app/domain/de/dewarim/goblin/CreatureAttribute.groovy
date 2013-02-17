package de.dewarim.goblin

import de.dewarim.goblin.combat.CombatAttributeType

/**
 * Mapping class between Creature and CombatAttributeType.
 * A creature may have several combat attributes. For example,
 * a dragon can breath fire or poison and his damage is accordingly (unless
 * the player character has a resistance against this combat attribute).
 */
class CreatureAttribute implements ICombatAttribute{

    static belongsTo = [creature:Creature, combatAttributeType:CombatAttributeType]

    Double damageModifier = 1.0

    CombatAttributeType fetchCombatAttributeType(){
        return combatAttributeType
    }

    Double fetchDamageModifier(){
        return damageModifier
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof CreatureAttribute)) return false

        CreatureAttribute that = o

        if (combatAttributeType != that.combatAttributeType) return false
        if (creature != that.creature) return false
        if (damageModifier != that.damageModifier) return false

        return true
    }

    int hashCode() {
        int result
        result = (damageModifier != null ? damageModifier.hashCode() : 0)
        result = 31 * result + (creature != null ? creature.hashCode() : 0)
        result = 31 * result + (combatAttributeType != null ? combatAttributeType.hashCode() : 0)
        return result
    }
}
