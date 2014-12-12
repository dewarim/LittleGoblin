package de.dewarim.goblin.combat

import de.dewarim.goblin.FighterState
import de.dewarim.goblin.pc.PlayerCharacter

class MeleeFighter {

    MeleeFighter() {}

    MeleeFighter(Melee melee, PlayerCharacter pc) {
        this.melee = melee
        this.pc = pc
    }

    Melee melee
    FighterState state = FighterState.ACTIVE
    PlayerCharacter pc

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof MeleeFighter)) return false

        MeleeFighter that = o
        if (melee != that.melee) return false
        if (pc != that.pc) return false
        if (state != that.state) return false

        return true
    }

    int hashCode() {
        int result
        result = pc != null ? pc.hashCode() : 0
        result = 31 * result + (melee != null ? melee.hashCode() : 0)
        return result
    }
}
