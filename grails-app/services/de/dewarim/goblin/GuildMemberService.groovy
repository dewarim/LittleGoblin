package de.dewarim.goblin

import de.dewarim.goblin.guild.Guild
import de.dewarim.goblin.guild.GuildMember
import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class GuildMemberService {

    void joinGuild(PlayerCharacter pc, Guild guild){
        GuildMember gm = new GuildMember(pc:pc, guild:guild)
        pc.addToGuildMemberships(gm)
        guild.addToGuildMembers(gm)
        gm.save()
    }

    void leaveGuild(PlayerCharacter pc, Guild guild){
        GuildMember gm = GuildMember.findWhere(pc:pc, guild:guild)
        pc.removeFromGuildMemberships(gm)
        guild.removeFromGuildMembers(gm)
        gm.delete()
    }

    Boolean checkMembership(PlayerCharacter pc, Guild guild){
        GuildMember gm = GuildMember.findWhere(pc:pc, guild:guild)
        return gm != null
    }

    Collection<Guild> fetchGuilds(PlayerCharacter pc, Integer max, Integer offset){
        def guilds = Guild.findAll(
                "from Guild as g where g in (select gm.guild from GuildMember as gm where gm.pc=:pc)",
                [pc:pc],[max:max, offset:offset]
        )
        return guilds
    }

}
