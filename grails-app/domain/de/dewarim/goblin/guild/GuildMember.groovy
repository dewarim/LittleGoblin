package de.dewarim.goblin.guild

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class GuildMember {

    static belongsTo = [guild:Guild, pc:PlayerCharacter]

    static constraints = {
        guild unique: 'pc'
    }

    static mapping = {
        version:false
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof GuildMember)) return false

        GuildMember that = o

        if (guild != that.guild) return false
        if (pc != that.pc) return false

        return true
    }

    int hashCode() {
        int result
        result = (pc != null ? pc.hashCode() : 0)
        result = 31 * result + (guild != null ? guild.hashCode() : 0)
        return result
    }
}
