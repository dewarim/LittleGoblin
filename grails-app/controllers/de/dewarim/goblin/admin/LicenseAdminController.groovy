package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.License
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Image

@Secured(["ROLE_ADMIN"])
class LicenseAdminController extends BaseController {

    def index() {
        return [
                licenses: License.listOrderByName()
        ]
    }

    def edit() {
        def license = License.get(params.id)
        if (!license) {
            render(status: 503, text: message(code: 'error.unknown.license'))
            return
        }
        render(template: '/licenseAdmin/edit', model: [license: license])
    }

    def cancelEdit() {
        def license = License.get(params.id)
        if (!license) {
            render(status: 503, text: message(code: 'error.unknown.license'))
            return
        }
        render(template: '/licenseAdmin/update', model: [license: license])
    }

    def update() {
        try {
            def license = License.get(params.id)
            if (!license) {
                throw new RuntimeException('error.unknown.license')
            }
            log.debug("update for ${license.name}")
            updateFields license
            license.save()
            render(template: '/licenseAdmin/list', model: [licenses: License.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(license) {
        license.name = inputValidationService.checkAndEncodeName(params.name, license)
        license.description =
            inputValidationService.checkAndEncodeText(params, "description", "license.description")
        license.url =
            inputValidationService.checkAndEncodeText(params, "url", "license.url")
    }

    def save() {
        License license = new License()
        try {
            updateFields(license)
            license.save()
            render(template: '/licenseAdmin/list', model: [licenses: License.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        License license = License.get(params.id)
        try {
            if (!license) {
                throw new RuntimeException("error.object.not.found")
            }
            if (Image.findByLicense(license)) {
                throw new RuntimeException("error.license.inUse")
            }
            license.delete()
            render(text: message(code: 'license.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
