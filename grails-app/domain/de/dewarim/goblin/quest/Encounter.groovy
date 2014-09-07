package de.dewarim.goblin.quest

import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.mob.EncounterMob

/**
 * An encounter may be reused in many quests.
 *
 */
class Encounter {

	static hasMany = [steps:QuestStep, mobs:EncounterMob]
    static mappedBy = [steps:'encounter']
	static constraints = {
	    script nullable:true
        config nullable:true, size:1..10241024
	}

    String name
	Boolean includesCombat = true
    GoblinScript script
    String config

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Encounter)) return false

        Encounter encounter = o

        if (config != encounter.config) return false
        if (includesCombat != encounter.includesCombat) return false
        if (name != encounter.name) return false
        if (script != encounter.script) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (script != null ? script.hashCode() : 0)
        result = 31 * result + (config != null ? config.hashCode() : 0)
        return result
    }
}
