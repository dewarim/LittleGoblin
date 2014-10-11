package de.dewarim.goblin.town

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
        name nullable: false, blank: false, unique: 'town'
        description blank: false
        town nullable: false
    }

    String name
    String description

    /**
     * Remove all guilds from guildList from this academy.
     * @param guildList
     */
    void purgeGuildAcademies(guildList){
        // TODO: implement purgeGuildAcademies
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Academy)) return false

        Academy academy = o

        if (description != academy.description) return false
        if (name != academy.name) return false
        if (town != academy.town) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (town != null ? town.hashCode() : 0)
        return result
    }
}
