package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Feature
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.shop.Shop
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class FeatureAdminController extends BaseController {

    def index() {
        return [
                features: Feature.listOrderByName()
        ]
    }

    def edit() {
        def feature = Feature.get(params.id)
        if (!feature) {
            return render(status: 503, text: message(code: 'error.unknown.feature'))
        }
        render(template: '/featureAdmin/edit', model: [feature: feature,
        ])
        return
    }

    def cancelEdit() {
        def feature = Feature.get(params.id)
        if (!feature) {
            return render(status: 503, text: message(code: 'error.unknown.feature'))
        }
        render(template: '/featureAdmin/row', model: [feature: feature])
        return
    }

    def update() {
        try {
            def feature = Feature.get(params.id)
            if (!feature) {
                throw new RuntimeException('error.unknown.feature')
            }
            log.debug("update for ${feature.name}")
            updateFields feature
            feature.save()
            render(template: '/featureAdmin/row', model: [feature: feature])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(feature) {
        feature.name = inputValidationService.checkAndEncodeName(params.name, feature)
        feature.internalName = inputValidationService.checkAndEncodeText(params, "internalName", "feature.internalName")
        if(grailsApplication.config.featureScripts?.find{it == params.script}){
            feature.script =  Class.forName(params.script, true, Thread.currentThread().contextClassLoader)
        }
        else{
            throw new RuntimeException("feature.script.missing")
        }

    }

    def save() {
        Feature feature = new Feature()
        try {
            updateFields(feature)
            feature.save()
            render(template: '/featureAdmin/list', model: [features: Feature.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Feature feature = Feature.get(params.id)
        try {
            if (!feature) {
                throw new RuntimeException("error.object.not.found")
            }

            feature.delete()
            render(text: message(code: 'feature.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
