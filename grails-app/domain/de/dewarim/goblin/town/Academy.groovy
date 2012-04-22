package de.dewarim.goblin.town

import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.guild.Guild

/**
 *  An Academy is a place where PCs may learn new Skills / SkillSets.
 * Currently, there is a 1:n mapping of Academy to SkillSets. One expansion
 * option is to add a course class which contains price and learning time of
 * SkillSets. But that is probably additional complexity without much play value,
 * unless there are different towns with multiple academies.
 *
 *
 * An Academy may be associated with a guild, and if that's the case, a PC may only
 * learn skills there if he is a member. 
 */
class Academy {

    static hasMany = [academySkillSets:AcademySkillSet, guildAcademies:GuildAcademy, academyLevels:AcademyLevel]
    static belongsTo = [town:Town]

    static constraints = {
    }

    String name
    String description

    /**
     * Remove all guilds from guildList from this academy.
     * @param guildList
     */
    void purgeGuildAcademies(guildList){

    }
}
