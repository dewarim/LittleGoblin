package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter

class HighScore {

	PlayerCharacter character
	Long xp
	Creature killer

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof HighScore)) return false

        HighScore highScore = o

        if (character != highScore.character) return false
        if (killer != highScore.killer) return false
        if (xp != highScore.xp) return false

        return true
    }

    int hashCode() {
        return character != null ? character.hashCode() : 0
    }
}
