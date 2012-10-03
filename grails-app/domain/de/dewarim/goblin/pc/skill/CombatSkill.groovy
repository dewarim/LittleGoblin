package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.Dice

/**
 * A CombatSkill which grants the owner a set of bonuses, once for each level.
 */
class CombatSkill extends Skill{

    /*
     * TODO: replace with a comprehensive skill system,
     * which also checks dependencies (for example, equipped weapons)
     * and may change CombatAttributes as well as offer
     * skills which can be activated for special attacks.
     *
     * And a pony, too.
     */

    static constraints = {
        strike nullable:true
        initiative nullable:true
        parry nullable: true
        damage nullable:true
    }
    
    Dice strike
    Dice initiative
    Dice parry
    Dice damage

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof CombatSkill)) return false

        CombatSkill that = (CombatSkill) o

        if (damage != that.damage) return false
        if (initiative != that.initiative) return false
        if (parry != that.parry) return false
        if (strike != that.strike) return false

        return true
    }

    int hashCode() {
        int result
        result = (strike != null ? strike.hashCode() : 0)
        result = 31 * result + (initiative != null ? initiative.hashCode() : 0)
        result = 31 * result + (parry != null ? parry.hashCode() : 0)
        result = 31 * result + (damage != null ? damage.hashCode() : 0)
        return result
    }
}
