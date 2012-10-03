package de.dewarim.goblin.combat

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.FighterState

class MeleeFighter {

    static belongsTo = [melee:Melee]

    static constraints = {
        action nullable: true
        pc nullable: true
        // pc only needs to be nullable during initialization
        // (when Hibernate loads this class and tries to instantiate it) and testing.
    }

    MeleeFighter(){}

    MeleeFighter(Melee melee, PlayerCharacter pc){
        this.melee = melee
        this.pc = pc
        this.melee.fighters.add(this)
        this.pc.currentMelee = melee
    }

    MeleeAction action

    Integer place = 0
    Integer round = 1

    FighterState state = FighterState.ACTIVE
    PlayerCharacter pc

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof MeleeFighter)) return false

        MeleeFighter that = (MeleeFighter) o

        if (action != that.action) return false
        if (melee != that.melee) return false
        if (pc != that.pc) return false
        if (place != that.place) return false
        if (round != that.round) return false
        if (state != that.state) return false

        return true
    }

    int hashCode() {
        int result
        result = (action != null ? action.hashCode() : 0)
        result = 31 * result + (place != null ? place.hashCode() : 0)
        result = 31 * result + (round != null ? round.hashCode() : 0)
        result = 31 * result + (state != null ? state.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        result = 31 * result + (melee != null ? melee.hashCode() : 0)
        return result
    }
}
