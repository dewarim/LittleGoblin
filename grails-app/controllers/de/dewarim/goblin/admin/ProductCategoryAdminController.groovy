package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.crafting.ProductCategory
import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.pc.crafting.Product

@Secured(["ROLE_ADMIN"])
class ProductCategoryAdminController extends BaseController {

    def index() {
        return [
                categories: ProductCategory.listOrderByName()
        ]
    }

    def edit() {
        def category = ProductCategory.get(params.id)
        if (!category) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: 'edit', model: [category: category])
    }

    def cancelEdit() {
        def category = ProductCategory.get(params.id)
        if (!category) {
            render(status: 503, text: message(code: 'error.object.not.found'))
            return
        }
        render(template: 'update', model: [category: category])
    }

    def update() {
        try {
            def category = ProductCategory.get(params.id)
            if (!category) {
                throw new RuntimeException('error.object.not.found')
            }
            log.debug("update for ${category.name}")
            updateFields category
            category.save()
            render(template: 'list', model: [categories: ProductCategory.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(category) {
        category.name = inputValidationService.checkAndEncodeName(params.name, category)
        if(params.description){
            category.description =
                inputValidationService.checkAndEncodeText(params, "description", "category.description")
        }
        else{
            category.description = null
        }
    }

    def save() {
        ProductCategory category = new ProductCategory()
        try {
            updateFields(category)
            category.save()
            render(template: 'list', model: [categories: ProductCategory.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        ProductCategory category = ProductCategory.get(params.id)
        try {
            if (!category) {
                throw new RuntimeException("error.object.not.found")
            }
            if (Product.findByCategory(category)) {
                throw new RuntimeException("error.product.category.inUse")
            }
            category.delete()
            render(text: message(code: 'product.category.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
