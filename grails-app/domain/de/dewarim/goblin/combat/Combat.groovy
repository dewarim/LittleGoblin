package de.dewarim.goblin.combat

import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.pc.PlayerCharacter

class Combat {

	static hasMany = [players:PlayerCharacter, mobs:Mob, messages:CombatMessage]
	static belongsTo = [playerCharacter:PlayerCharacter]
	static constraints = {
		finished(nullable:true)
	}
    static mapping = {
        version:false
    }

    Date started = new Date()
	Date finished

    Combat(){}

	Combat(PlayerCharacter pc, Mob mob){
		this.playerCharacter = pc
		this.addToPlayers(pc)
		this.addToMobs(mob)
	}

	/**
	 * Currently, a combat has only one mob. This is a temporary convenience method
	 * to get this mob.
	 * @return
	 */
	Mob fetchFirstMob(){
		return mobs ? mobs.asList()[0] : null
	}

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Combat)) return false

        Combat combat = o

        if (finished != combat.finished) return false
        if (playerCharacter != combat.playerCharacter) return false
        if (started != combat.started) return false

        return true
    }

    int hashCode() {
        int result
        result = (started != null ? started.hashCode() : 0)
        result = 31 * result + (finished != null ? finished.hashCode() : 0)
        result = 31 * result + (playerCharacter != null ? playerCharacter.hashCode() : 0)
        return result
    }
}
