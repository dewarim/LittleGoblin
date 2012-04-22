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

}
