package de.dewarim.goblin.combat;

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
		if(mobs.isEmpty()){
			return null
		}
		return mobs.asList().get(0)
	}
}
