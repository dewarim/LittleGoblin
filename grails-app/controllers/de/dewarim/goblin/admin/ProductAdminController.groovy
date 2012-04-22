package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Image
import de.dewarim.goblin.pc.crafting.Product
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.crafting.ProductCategory

@Secured(["ROLE_ADMIN"])
class ProductAdminController extends BaseController {

    def inputValidationService

    def index = {
        return [
                products: Product.listOrderByName()
        ]
    }

    def edit = {
        def product = Product.get(params.id)
        if (!product) {
            return render(status: 503, text: message(code: 'error.unknown.product'))
        }
        render(template: '/productAdmin/edit', model: [product: product])
        return
    }

    def cancelEdit = {
        def product = Product.get(params.id)
        if (!product) {
            return render(status: 503, text: message(code: 'error.unknown.product'))
        }
        render(template: '/productAdmin/update', model: [product: product])
        return
    }

    def update = {
        try {
            def product = Product.get(params.id)
            if (!product) {
                throw new RuntimeException('error.unknown.product')
            }
            log.debug("update for ${product.name}")
            updateFields product
            product.save()
            render(template: '/productAdmin/list', model: [products: Product.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    void updateFields(product) {
        product.name = inputValidationService.checkAndEncodeName(params.name, product)
        product.timeNeeded =
            inputValidationService.checkAndEncodeInteger(params, "timeNeeded", "product.timeNeeded")
        product.category = inputValidationService.checkObject(ProductCategory.class, params.category)
        // productionScript is currently disabled - too much complexity.
//        try {
//            Class script = Class.forName(params.productionScript).newInstance()
//            product.productionScript = Class.forName(params.productionScript)
//        }
//        catch (ClassCastException e) {
//            throw new RuntimeException("error.wrong.class")
//        }
//        catch (ClassNotFoundException e) {
//            throw new RuntimeException("error.class.not.found")
//        }
//        catch (Exception e) {
//            throw new RuntimeException("error.class.broken")
//        }
    }

    def save = {
        Product product = new Product()
        try {
            updateFields(product)
            product.save()
            render(template: '/productAdmin/list', model: [products: Product.listOrderByName()])
        }
        catch (RuntimeException e) {
//            log.debug("failed to save: ", e)
            renderException(e)
        }
    }

    def delete = {
        Product product = Product.get(params.id)
        try {
            if (!product) {
                throw new RuntimeException("error.object.not.found")
            }
            product.deleteFully()
            render(text: message(code: 'product.deleted'))
        }
        catch (RuntimeException e) {
            log.debug("failed to delete.",e)
            renderException e
        }
    }

}
