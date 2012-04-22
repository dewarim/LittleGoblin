package de.dewarim.goblin.quest;
import de.dewarim.goblin.IEncounterScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.mob.EncounterMob;

/**
 * An encounter may be reused in many quests.
 *
 */
class Encounter {
	
	static hasMany = [steps:QuestStep, mobs:EncounterMob]
	static constraints = {
	    script nullable:true
        config nullable:true, size:1..10241024
	}

    String name
	Boolean includesCombat = true
    GoblinScript script
    String config

}
