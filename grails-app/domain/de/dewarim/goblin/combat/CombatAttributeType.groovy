package de.dewarim.goblin.combat

import de.dewarim.goblin.AttributeType
import de.dewarim.goblin.CreatureAttribute

/**
 *
 */

class CombatAttributeType {

    String name
    AttributeType subType = AttributeType.ATTACK
    
    static hasMany = [weaponAttributes:WeaponAttribute, creatureAttributes:CreatureAttribute]
    static constraints = {
        name(blank:false, unique:true)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof CombatAttributeType)) return false

        CombatAttributeType that = (CombatAttributeType) o

        if (name != that.name) return false
        if (subType != that.subType) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}