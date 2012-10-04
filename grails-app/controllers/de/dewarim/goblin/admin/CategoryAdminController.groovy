package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Category
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.shop.Shop

@Secured(["ROLE_ADMIN"])
class CategoryAdminController extends BaseController {

    def categoryService

    def index() {
        return [
                categories: Category.listOrderByName()
        ]
    }

    def edit() {
        def category = Category.get(params.id)
        if (!category) {
            return render(status: 503, text: message(code: 'error.unknown.category'))
        }
        render(template: '/categoryAdmin/edit', model: [category: category,
                selectedShops: categoryService.fetchSelectedShops(category),
                selectedItemTypes: categoryService.fetchSelectedItemTypes(category)
        ])
        return
    }

    def cancelEdit() {
        def category = Category.get(params.id)
        if (!category) {
            return render(status: 503, text: message(code: 'error.unknown.category'))
        }
        render(template: '/categoryAdmin/row', model: [category: category])
        return
    }

    def update() {
        try {
            def category = Category.get(params.id)
            if (!category) {
                throw new RuntimeException('error.unknown.category')
            }
            log.debug("update for ${category.name}")
            updateFields category
            category.save()
            updateRelations category
            render(template: '/categoryAdmin/row', model: [category: category])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    protected void updateFields(category) {
        category.name = inputValidationService.checkAndEncodeName(params.name, category)

    }

    protected void updateRelations(category) {
        def itemTypes = params.list("itemTypes").collect {
            inputValidationService.checkObject ItemType.class, it
        }
        categoryService.updateItemCategories(category, itemTypes)
        def shops = params.list("shops").collect {
            inputValidationService.checkObject Shop.class, it
        }
        categoryService.updateShopCategories(category, shops)
    }

    def save() {
        Category category = new Category()
        try {
            updateFields(category)
            category.save()
            updateRelations category
            render(template: '/categoryAdmin/list', model: [categories: Category.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Category category = Category.get(params.id)
        try {
            if (!category) {
                throw new RuntimeException("error.object.not.found")
            }
            category.itemCategories.each {
                it.deleteFully()
            }
            category.shopCategories.each {
                it.deleteFully()
            }
            category.delete()
            render(text: message(code: 'category.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
