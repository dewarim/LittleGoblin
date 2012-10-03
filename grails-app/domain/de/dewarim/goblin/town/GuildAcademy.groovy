package de.dewarim.goblin.town

import de.dewarim.goblin.guild.Guild

/**
 *  Mapping class between Guild and Academy.
 */
class GuildAcademy {

    static belongsTo = [guild:Guild, academy:Academy]

    GuildAcademy() {
    }

    /**
     * Create a new GuildAcademy instance and add it to both relation sets
     * (in guild.guildAcademies and academy.guildAcademies)
     * @param guild
     * @param academy
     */
    GuildAcademy(Guild guild, Academy academy) {
        this.guild = guild
        this.academy = academy
        guild.addToGuildAcademies this
        academy.addToGuildAcademies this
    }

    /**
     * Remove GuildAcademy from both relation sets and then delete it.
     */
    void deleteComplete(){
        guild.guildAcademies.remove this
        academy.guildAcademies.remove this
        this.delete()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof GuildAcademy)) return false

        GuildAcademy that = (GuildAcademy) o

        if (academy != that.academy) return false
        if (guild != that.guild) return false

        return true
    }

    int hashCode() {
        int result
        result = (guild != null ? guild.hashCode() : 0)
        result = 31 * result + (academy != null ? academy.hashCode() : 0)
        return result
    }
}
