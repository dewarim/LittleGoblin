package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.pc.skill.Skill

/**
 * This is a mapping table between a Product and a Skill. If a product needs a special
 * skill to create, a SkillRequirement is created to define this relation.
 */
class SkillRequirement {

    static belongsTo = [product:Product, skill:Skill]

    /**
    * The minimum required level of the skill.
     */
    Integer level = 1

    void deleteFully(){
        product.removeFromRequiredSkills(this)
        skill.removeFromSkillRequirements(this)
        delete()
    }
}
