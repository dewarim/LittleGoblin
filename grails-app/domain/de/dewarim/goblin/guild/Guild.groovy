package de.dewarim.goblin.guild

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.GuildAcademy

/**
 *
 */
class Guild {

    static hasMany = [guildMembers:GuildMember, guildAcademies:GuildAcademy]
    String name
    String description
    Integer entryFee = 1
    // TODO: implement incomeTax.
    Double incomeTax = 0.99 // a 1 percent tax on all income. 
    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Guild)) return false

        Guild guild = (Guild) o

        if (description != guild.description) return false
        if (entryFee != guild.entryFee) return false
        if (incomeTax != guild.incomeTax) return false
        if (name != guild.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (entryFee != null ? entryFee.hashCode() : 0)
        result = 31 * result + (incomeTax != null ? incomeTax.hashCode() : 0)
        return result
    }
}
