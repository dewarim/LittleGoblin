package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.guild.Guild
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.GuildAcademy

@Secured(["ROLE_ADMIN"])
class GuildAdminController extends BaseController {

    def index() {
        return [
                guilds: Guild.listOrderByName()
        ]
    }

    def edit(Long id) {
        def guild = Guild.get(id)
        if (!guild) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/guildAdmin/edit', model: [guild: guild])
    }

    def cancelEdit(Long id) {
        def guild = Guild.get(id)
        if (!guild) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: '/guildAdmin/update', model: [guild: guild])
    }

    def update(Long id) {
        try {
            def guild = Guild.get(id)
            if (!guild) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${guild.name}")
            updateFields guild
            updateGuildAcademies guild

            guild.save()
            render(template: '/guildAdmin/update', model: [guild: guild])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update guild:",e)
            renderException e
        }
    }

    protected void updateGuildAcademies(Guild guild) {
        def academyList = params.list("academies").collect { academy ->
            inputValidationService.checkObject(Academy.class, academy)
        }
        // remove deselected academies
        guild.guildAcademies.each {ga ->
            log.debug("GuildAcademy: ${ga.dump()}")
            if (! academyList.find {it.equals(ga.academy)}) {
                ga.deleteComplete()
            }
        }
        // add new academies if necessary
        academyList.each {Academy academy ->
            log.debug "Academy: ${academy}"
            if (!guild.guildAcademies.find {it.academy.equals(academy)}) {
                GuildAcademy ga = new GuildAcademy(guild, academy)
                ga.save()
            }
        }
    }

    protected void updateFields(guild){
        guild.name = inputValidationService.checkAndEncodeName(params.name, guild)
        guild.description =
            inputValidationService.checkAndEncodeText(params, "description", "guild.description")
        // TODO: default entryFee and incomeTax should be defined as global constants.
        guild.entryFee = params.entryFee ? Integer.parseInt(params.entryFee) : 1
        guild.incomeTax = params.incomeTax ? Double.parseDouble(params.incomeTax) : 0.99
    }

    def save() {
        Guild guild = new Guild()
        try {
           updateFields(guild)
           guild.save()
           updateGuildAcademies(guild)

           render(template: '/guildAdmin/list', model: [guilds: Guild.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save guild", e
            renderException(e)
        }
    }

    def delete(Long id) {
        Guild guild = Guild.get(id)
        try {
            if (!guild) {
                throw new RuntimeException("error.object.not.found")
            }
            guild.delete()
            render(text: message(code: 'guild.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
