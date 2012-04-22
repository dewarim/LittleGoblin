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
}
