package de.dewarim.goblin

import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.pc.PlayerCharacter

class HighScore {

    PlayerCharacter character
    Long xp
    PlayerCharacter killerChar
    Mob killerMob

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof HighScore)) return false

        HighScore highScore = (HighScore) o

        if (character != highScore.character) return false
        if (killerChar != highScore.killerChar) return false
        if (killerMob != highScore.killerMob) return false
        if (xp != highScore.xp) return false

        return true
    }

    int hashCode() {
        return character.hashCode()
    }

    Creature getKiller(){
        killerChar ?: killerMob
    }
}
