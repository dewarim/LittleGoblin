package de.dewarim.goblin.town

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * Maps the level a player character has achieved in a specific academy.
 * Depending on the level, different skill-sets are offered for learning.
 */
class AcademyLevel {

    static belongsTo = [pc:PlayerCharacter, academy:Academy]
    Integer level = 1

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof AcademyLevel)) return false

        AcademyLevel that = (AcademyLevel) o

        if (academy != that.academy) return false
        if (level != that.level) return false
        if (pc != that.pc) return false

        return true
    }

    int hashCode() {
        int result
        result = (level != null ? level.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        result = 31 * result + (academy != null ? academy.hashCode() : 0)
        return result
    }
}
