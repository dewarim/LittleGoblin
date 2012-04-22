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

}
