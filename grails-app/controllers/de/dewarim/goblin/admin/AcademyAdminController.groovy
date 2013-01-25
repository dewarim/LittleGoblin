package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.town.Academy
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.town.Town
import de.dewarim.goblin.guild.Guild
import de.dewarim.goblin.town.GuildAcademy

@Secured(["ROLE_ADMIN"])
class AcademyAdminController extends BaseController {

    def index() {
        return [
                academies: Academy.listOrderByName()
        ]
    }

    def edit() {
        def academy = Academy.get(params.id)
        if (!academy) {
            return render(status: 503, text: message(code: 'error.unknown.academy'))
        }
        render(template: '/academyAdmin/edit', model: [academy: academy])
    }

    def cancelEdit() {
        def academy = Academy.get(params.id)
        if (!academy) {
            return render(status: 503, text: message(code: 'error.unknown.academy'))
        }
        render(template: '/academyAdmin/update', model: [academy: academy])
    }

    def update() {
        try {
            def academy = Academy.get(params.id)
            if (!academy) {
                throw new RuntimeException('error.unknown.academy')
            }
            log.debug("update for ${academy.name}")
            updateFields academy
            updateGuildAcademies academy

            academy.save()
            render(template: '/academyAdmin/update', model: [academy: academy])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    void updateGuildAcademies(Academy academy) {
        def guildList = params.list("guilds").collect { guild ->
            inputValidationService.checkObject(Guild.class, guild)
        }
        // remove deselected guilds
        academy.guildAcademies.each {ga ->
            if (!guildList.find {it.equals(ga.guild)}) {
                ga.deleteComplete()
            }
        }
        // add new guilds if necessary
        guildList.each {Guild guild ->
            log.debug "Guild: ${guild}"
            if (!academy.guildAcademies.find {it.guild.equals(guild)}) {
                GuildAcademy ga = new GuildAcademy(guild, academy)
                ga.save()
            }
        }
    }

    void updateFields(academy){
        academy.name = inputValidationService.checkAndEncodeName(params.name, academy)
        academy.description =
            inputValidationService.checkAndEncodeText(params, "description", "academy.description")
        academy.town = (Town) inputValidationService.checkObject(Town.class, params.town)
    }

    def save() {
        Academy academy = new Academy()
        try {
           updateFields(academy)
           academy.save()
           updateGuildAcademies(academy)

           render(template: '/academyAdmin/list', model: [academies: Academy.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save academy", e
            renderException(e)
        }
    }

    def delete() {
        Academy academy = Academy.get(params.id)
        try {
            if (!academy) {
                throw new RuntimeException("error.object.not.found")
            }
            // TODO: check if this works with dependent objects.
            academy.delete()
            render(text: message(code: 'academy.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
