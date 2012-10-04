package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.Component
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.item.ItemType

@Secured(["ROLE_ADMIN"])
/**
 * Manage the components of a specific product.
 */
class ComponentAdminController extends BaseController {

    def index() {
        try {
            def product = inputValidationService.checkObject(Product.class, params.id)
            return [
                    product: product, component: null
            ]
        }
        catch (Exception e) {
            flash.message = message(code: 'error.invalid.object')
            return redirect(controller: 'productAdmin', action: 'index')
        }
    }

    def edit() {
        try {
            def component = inputValidationService.checkObject(Component.class, params.id)
            render(template: 'edit', model: [product: component.product, component: component])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def cancelEdit() {
        try {
            def component = inputValidationService.checkObject(Component.class, params.id)
            render(template: 'update', model: [product: component.product, component: component])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def update() {
        try {
            def component = inputValidationService.checkObject(Component.class, params.id)
            updateFields(component)
            component.save()
            render(template: 'update', model: [product: component.product, component: component])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    /**
     * Add a Component to a product. #AJAX
     */
    def save() {
        try {
            Product product = (Product) inputValidationService.checkObject(Product.class, params.product)
            Component component = new Component(product: product)
            updateFields(component)
            def exists = Component.find("from Component c where c.product=:product and c.itemType=:itemType and c.type=:type",
            [product:component.product, itemType:component.itemType, type:component.type])
            if(exists){
                throw new RuntimeException(message(code:"error.component.exists"))
            }

            product.addToComponents(component)
            component.save()
            render(template: 'list', model: [product: product, component: component])
        }
        catch (RuntimeException e) {
            renderException e
        }

    }

    /**
     * Delete a message from a reputationMessageMap. #AJAX
     */
    def delete() {
        try {
            Component component = (Component) inputValidationService.checkObject(Component, params.id)
            Product product = component.product
            component.delete()
            product.refresh()
            render(template: 'list', model: [product: product, component: component])
        }
        catch (RuntimeException e) {
            log.debug("failed to delete; ", e)
            renderException e
        }
    }

    protected void updateFields(Component component) {
        component.amount = inputValidationService.checkAndEncodeInteger(params, "amount", "component.amount")
        component.type = (ComponentType) inputValidationService.checkEnum(ComponentType.class,  params.type)
        component.itemType = (ItemType) inputValidationService.checkObject(ItemType.class, params.itemType)
    }

}
