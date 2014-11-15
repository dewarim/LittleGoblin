package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.ProductionJob

/**
 * A product is something a player can create, given the necessary materials and skill requirements.
 */
@Secured(['ROLE_USER'])
class ProductionController extends BaseController{

    def productionService

    /**
     * Show the list of available product categories.
     */
    def workshop() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def categories = ProductCategory.list()

        return [categories:categories,
                pc:pc
        ]
    }

    def listProducts() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def category = ProductCategory.get(params.category)
        if(! category){
            flash.message = message(code:'error.missing.category')
            redirect(action:'show', controller:'town')
            return
        }

        // return a list of all products which the user may create
        // in this category.
        def products = pc.playerProducts.findAll{ pp ->
            pp.product.category.equals(category)
        }.collect{
            it.product
        }
        return [products:products,
                pc:pc,
                category:category
        ]
    }

    def selectComponents() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def product = Product.get(params.product)
        if(product &&
                pc.playerProducts.find{it.product.equals(product)} ){
            def components = product.fetchInputItems()
            def maxItems = productionService.computeMaxProduction(product, pc)
            log.debug("maxItems: ${maxItems}")
            def itemMap = [:]
            if(maxItems > 0){
                itemMap = productionService.fetchItemMap(product, pc)
            }
            log.debug("itemMap: ${itemMap?.dump()}")
            return [pc:pc,
                    product:product,
                    maxItems:maxItems,
                    components:components,
                    itemMap:itemMap
            ]
        }
        else{
            flash.message = message(code:'error.missing.product')
            redirect(action:'workshop', controller:'production')
        }
    }

    def listProductionJobs() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def productionJobs = pc.productionJobs.sort{it.finished.time}

        [pc:pc, jobs:productionJobs]
    }

    // AJAX request:
    /**
     * After the player has selected the required items for each component
     * of this product, this method will try to create a ProductionJob for the
     * chosen product.
     */
    def startProduction() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def product = Product.get(params.product)

        // check if player may create this product
        if(product && pc.playerProducts.find{it.product.equals(product)}){
            // check if the player has selected enough resources.
            if( productionService.enoughResourcesSelected(product, pc, params) ){
                try{
                    def optionalResultJob = productionService.createNewProductionJob(product, pc, params)
                    if(optionalResultJob.hasErrors()){
                        // TODO: replace with proper error handling and rendering.
                        throw new RuntimeException(optionalResultJob.errors.join(""))
                    }
                    def itemMap = productionService.fetchItemMap(product, pc)
                    def selectedItems = productionService.fetchItemCountMapFromParams(params)
                    def msg = message(code:'productionJob.created', args:[message(code:product.name)])
                    log.debug("message: $msg")
                    def jobCount = pc.productionJobs.size()
                    render(template:'/production/selectItems',
                            model:[product:product,
                                    itemMap:itemMap,
                                    selectedItems:selectedItems,
                                    prodStarted:msg,
                                    jobCount:jobCount
                            ])
                    return
                }
                catch(RuntimeException ex){
                    log.debug("failed to create a new ProductionJob", ex)
                    render(status:503, text:message(code:ex.getMessage()))
                    return
                }
            }
            else{
                render(status:503, text:message(code:'error.insufficient.resources'))
            }
        }
        else{
            render(status:503, text:message(code:'error.missing.product'))
        }
    }

    /**
     * Cancel a ProductionJob. Checks if the job belongs to the active player.
     * Will redirect him to the workshop if no more jobs remain, otherwise
     * redirect back to the list of production jobs.
     */
    def cancelProductionJob() {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
            return
        }
        def job = ProductionJob.get(params.job)
        if(job){
            if(job.pc.equals(pc)){
                job.deleteFully()
                flash.message = message(code:'production.job.canceled')
                if(pc.productionJobs.size() > 0){
                    redirect(action:'listProductionJobs', controller:'production')
                    return
                }
            }
            else{
                flash.message = message(code:'error.object.foreign')
            }
        }
        else{
            flash.message = message(code:'error.job.not_found')
        }
        redirect(action:'workshop', controller:'production')
    }
}
