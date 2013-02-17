package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.SkillRequirement

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

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Skill)) return false

        Skill skill = o

        if (initParams != skill.initParams) return false
        if (name != skill.name) return false
        if (script != skill.script) return false
        if (startLevel != skill.startLevel) return false

        return true
    }

    int hashCode() {
        int result
        result = (script != null ? script.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (startLevel != null ? startLevel.hashCode() : 0)
        result = 31 * result + (initParams != null ? initParams.hashCode() : 0)
        return result
    }
}
