package de.dewarim.goblin.reputation

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * Mapping class between PlayerCharacter and Faction.
 */
class Reputation {

    static belongsTo = [pc:PlayerCharacter, faction:Faction]

    Integer level = 0

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Reputation)) return false

        Reputation that = o

        if (faction != that.faction) return false
        if (level != that.level) return false
        if (pc != that.pc) return false

        return true
    }

    int hashCode() {
        int result
        result = (level != null ? level.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        result = 31 * result + (faction != null ? faction.hashCode() : 0)
        return result
    }
}
