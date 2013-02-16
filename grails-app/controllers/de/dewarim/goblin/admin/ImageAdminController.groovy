package de.dewarim.goblin.admin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Artist
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Image
import de.dewarim.goblin.License
import de.dewarim.goblin.mob.MobImage

@Secured(["ROLE_ADMIN"])
class ImageAdminController extends BaseController {

    def index() {
        return [
                images: Image.listOrderByName()
        ]
    }

    def edit() {
        def image = Image.get(params.id)
        if (!image) {
            render(status: 503, text: message(code: 'error.unknown.image'))
            return
        }
        render(template: '/imageAdmin/edit', model: [image: image])
    }

    def cancelEdit() {
        def image = Image.get(params.id)
        if (!image) {
            render(status: 503, text: message(code: 'error.unknown.image'))
            return
        }
        render(template: '/imageAdmin/update', model: [image: image])
    }

    def update() {
        try {
            def image = Image.get(params.id)
            if (!image) {
                throw new RuntimeException('error.unknown.image')
            }
            log.debug("update for ${image.name}")
            updateFields image
            image.save()
            render(template: '/imageAdmin/list', model: [images: Image.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update image:",e)
            renderException e
        }
    }

    def showImage() {
        try{
            def image = Image.get(params.id)
            if (!image) {
                throw new RuntimeException('error.unknown.image')
            }
            render(template: '/imageAdmin/showImage', model: [image:image])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(image) {
        image.name = inputValidationService.checkAndEncodeName(params.name, image)
        image.description =
            inputValidationService.checkAndEncodeText(params, "description", "image.description")
        image.url =
            inputValidationService.checkAndEncodeText(params, "url", "image.url")
        if(params.sourceUrl){
            image.sourceUrl =
                inputValidationService.checkAndEncodeText(params, "sourceUrl", "image.sourceUrl")
        }
        image.license = inputValidationService.checkObject(License.class, params.license)
        image.artist = inputValidationService.checkObject(Artist.class, params.artist)
        image.height = inputValidationService.checkAndEncodeInteger(params, 'height', 'image.height')
        image.width = inputValidationService.checkAndEncodeInteger(params, 'width', 'image.width')
    }

    def save() {
        Image image = new Image()
        try {
            updateFields(image)
            image.save()
            render(template: '/imageAdmin/list', model: [images: Image.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug "failed to save image", e
            renderException(e)
        }
    }

    def delete() {
        Image image = Image.get(params.id)
        try {
            if (!image) {
                throw new RuntimeException("error.object.not.found")
            }
            if (MobImage.findByImage(image)) {
                throw new RuntimeException("error.image.inUse")
            }
            image.delete()
            render(text: message(code: 'image.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
