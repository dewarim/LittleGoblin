package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.Creature

/**
 *
 */
class CreatureSkill {

    /*
     * Mapping class between Creature and Skill.
     */

    static belongsTo = [owner:Creature, skill:Skill]
    Integer level = 1

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof CreatureSkill)) return false

        CreatureSkill that = (CreatureSkill) o

        if (level != that.level) return false
        if (owner != that.owner) return false
        if (skill != that.skill) return false

        return true
    }

    int hashCode() {
        int result
        result = (level != null ? level.hashCode() : 0)
        result = 31 * result + (skill != null ? skill.hashCode() : 0)
        result = 31 * result + (owner != null ? owner.hashCode() : 0)
        return result
    }
}
