package de.dewarim.goblin;

import de.dewarim.goblin.combat.CombatAttributeType;

/**
 *
 */
public interface ICombatAttribute {

    CombatAttributeType fetchCombatAttributeType();
    Double fetchDamageModifier();

}
