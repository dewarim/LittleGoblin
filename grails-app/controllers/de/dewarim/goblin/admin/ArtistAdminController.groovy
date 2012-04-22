package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Artist
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Artist

@Secured(["ROLE_ADMIN"])
class ArtistAdminController extends BaseController {

    def inputValidationService

    def index = {
        return [
                artists: Artist.listOrderByName()
        ]
    }

    def edit = {
        def artist = Artist.get(params.id)
        if (!artist) {
            return render(status: 503, text: message(code: 'error.unknown.artist'))
        }
        render(template: '/artistAdmin/edit', model: [artist: artist])
        return
    }

    def cancelEdit = {
        def artist = Artist.get(params.id)
        if (!artist) {
            return render(status: 503, text: message(code: 'error.unknown.artist'))
        }
        render(template: '/artistAdmin/update', model: [artist: artist])
        return
    }

    def update = {
        try {
            def artist = Artist.get(params.id)
            if (!artist) {
                throw new RuntimeException('error.unknown.artist')
            }
            log.debug("update for ${artist.name}")
            updateFields artist
            artist.save()
            render(template: '/artistAdmin/update', model: [artist: artist])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update artist:",e)
            renderException e
        }
    }

    protected void updateFields(artist){
        artist.name = inputValidationService.checkAndEncodeName(params.name, artist)
        if(params.website){ // website is optional (Albrecht Dürer does not have one)
            artist.website =
            inputValidationService.checkAndEncodeText(params, "website", "artist.website")
        }
    }

    def save = {
        Artist artist = new Artist()
        try {
           updateFields(artist)
           artist.save()

           render(template: '/artistAdmin/list', model: [artists: Artist.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save artist", e
            renderException(e)
        }
    }

    def delete = {
        Artist artist = Artist.get(params.id)
        try {
            if (!artist) {
                throw new RuntimeException("error.object.not.found")
            }
            if(artist.images.isEmpty()){
                artist.delete()
                render(text: message(code: 'artist.deleted'))
            }
            else{
                throw new RuntimeException('error.artist.has.images')
            }
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
