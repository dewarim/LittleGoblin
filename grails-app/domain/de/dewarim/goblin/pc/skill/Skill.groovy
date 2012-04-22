package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class Skill {

    static hasMany = [creatureSkills:CreatureSkill, skillRequirements:SkillRequirement] // TODO: is this reverse mapping required?
    static constraints = {
        script nullable:true
        initParams size:1..4096
    }

    Class script

    String name
    Integer startLevel = 1
    String initParams = "<initParams />"

    void initSkill(PlayerCharacter pc){
        // do nothing
    }
}
