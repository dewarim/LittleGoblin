package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.pc.skill.Skill

/**
 * This is a mapping table between a Product and a Skill. If a product needs a special
 * skill to create, a SkillRequirement is created to define this relation.
 */
class SkillRequirement {

    Product product
    Skill skill
    
    /**
    * The minimum required level of the skill.
     */
    Integer level = 1

    void deleteFully(){
        product.removeFromRequiredSkills(this)
        skill.removeFromSkillRequirements(this)
        delete()
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof SkillRequirement)) return false

        SkillRequirement that = o

        if (level != that.level) return false
        if (product != that.product) return false
        if (skill != that.skill) return false

        return true
    }

    int hashCode() {
        int result
        result = (level != null ? level.hashCode() : 0)
        result = 31 * result + (product != null ? product.hashCode() : 0)
        result = 31 * result + (skill != null ? skill.hashCode() : 0)
        return result
    }
}
