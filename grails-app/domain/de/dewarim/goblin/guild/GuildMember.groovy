package de.dewarim.goblin.guild

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class GuildMember {

    static belongsTo = [guild:Guild, pc:PlayerCharacter]
    
}
