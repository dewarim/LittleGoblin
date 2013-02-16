package de.dewarim.goblin.mob

import de.dewarim.goblin.quest.Encounter

/**
 * Mapping class Encounter::MobTemplate
 */
class EncounterMob {

    static belongsTo = [mob:MobTemplate, encounter:Encounter]

    EncounterMob(){}

    EncounterMob(Encounter encounter, MobTemplate mob){
        this.encounter = encounter
        this.mob = mob
        encounter.addToMobs(this)
        mob.addToEncounterMobs(this)
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof EncounterMob)) return false

        EncounterMob that = o

        if (encounter != that.encounter) return false
        if (mob != that.mob) return false

        return true
    }

    int hashCode() {
        int result
        result = (mob != null ? mob.hashCode() : 0)
        result = 31 * result + (encounter != null ? encounter.hashCode() : 0)
        return result
    }
}
