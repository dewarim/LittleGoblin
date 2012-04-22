package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.Creature

/**
 *
 */
class CreatureSkill {

    /*
     * Mapping class between Creature and Skill.
     */

    static belongsTo = [owner:Creature, skill:Skill]
    Integer level = 1

}
