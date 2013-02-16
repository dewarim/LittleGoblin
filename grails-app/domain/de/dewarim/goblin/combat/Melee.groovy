package de.dewarim.goblin.combat

import de.dewarim.goblin.MeleeStatus
import de.dewarim.goblin.pc.PlayerCharacter

class Melee{

    static hasMany = [fighters:MeleeFighter]

    static constraints = {
        winner nullable: true
        startTime( nullable: true)
    }

    MeleeStatus status = MeleeStatus.WAITING
    Date startTime
    PlayerCharacter winner
    Integer round = 1

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Melee)) return false

        Melee melee = o

        if (round != melee.round) return false
        if (startTime != melee.startTime) return false
        if (status != melee.status) return false
        if (winner != melee.winner) return false

        return true
    }

    int hashCode() {
        int result
        result = (status != null ? status.hashCode() : 0)
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0)
        result = 31 * result + (winner != null ? winner.hashCode() : 0)
        result = 31 * result + (round != null ? round.hashCode() : 0)
        return result
    }
}