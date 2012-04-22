package de.dewarim.goblin.combat

import de.dewarim.goblin.CreatureAttribute

/**
 *
 */

class CombatAttributeType {

    String name

    static hasMany = [weaponAttributes:WeaponAttribute, creatureAttributes:CreatureAttribute]
    static constraints = {
        name(blank:false, unique:true)
    }
    
}