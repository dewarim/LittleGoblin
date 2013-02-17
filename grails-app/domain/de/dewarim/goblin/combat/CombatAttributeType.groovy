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

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof CombatAttributeType)) return false

        CombatAttributeType that = o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}