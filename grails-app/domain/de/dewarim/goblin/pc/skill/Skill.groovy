package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.ISkillScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.SkillRequirement

/**
 *
 */
abstract class Skill {

    static constraints = {
        script nullable: true, validator: {
            return it == null || (it in ISkillScript)
        }
        initParams size: 12..4096
        name unique: true, blank: false
    }

    Class script

    String name
    Integer startLevel = 1
    String initParams = "<initParams />"

    void initSkill(PlayerCharacter pc) {
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
        return name != null ? name.hashCode() : 0
    }
    
    List<CreatureSkill> getCreatureSkills(){
        return CreatureSkill.findAllBySkill(this)
    }
    
    List<SkillRequirement> getSkillRequirements(){
        return SkillRequirement.findAllBySkill(this)
    }
    
}
