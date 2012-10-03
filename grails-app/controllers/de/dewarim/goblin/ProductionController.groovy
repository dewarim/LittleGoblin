package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductionJob

/**
 * A product is something a player can create, given the necessary materials and skill requirements.
 */
class ProductionController extends BaseController{

    def productionService

    /**
     * Show the list of available product categories.
     */
    @Secured(['ROLE_USER'])
    def workshop = {
        def pc = fetchPc()
        if(! pc){
            return redirect(action:'start', controller:'portal')
        }
        def categories = ProductCategory.list()
        
        return [categories:categories,
                pc:pc
        ]
    }

    @Secured(['ROLE_USER'])
    def listProducts = {
        def pc = fetchPc()
        if(! pc){
            return redirect(action:'start', controller:'portal')
        }
        def category = ProductCategory.get(params.category)
        if(! category){
            flash.message = message(code:'error.missing.category')
            return redirect(action:'show', controller:'town')
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

    @Secured(['ROLE_USER'])
    def selectComponents = {
        def pc = fetchPc()
        if(! pc){
            redirect(action:'start', controller:'portal')
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
            return redirect(action:'workshop', controller:'production')
        }
    }

    @Secured(['ROLE_USER'])
    def listProductionJobs = {
        def pc = fetchPc()
        if(! pc){
            return redirect(action:'start', controller:'portal')
        }
        def productionJobs = pc.productionJobs.sort{it.finished.time}

        return [pc:pc,
                jobs:productionJobs
        ]
    }

    // AJAX request:
    /**
     * After the player has selected the required items for each component
     * of this product, this method will try to create a ProductionJob for the
     * chosen product.
     */
    @Secured(['ROLE_USER'])
    def startProduction = {
        def pc = fetchPc()
        if(! pc){
            return redirect(action:'start', controller:'portal')
        }
        def product = Product.get(params.product)
        
        // check if player may create this product
        if(product && pc.playerProducts.find{it.product.equals(product)}){
            // check if the player has selected enough resources.
            if( productionService.enoughResourcesSelected(product, pc, params) ){
                try{
                    productionService.createNewProductionJob(product, pc, params)
                    def itemMap = productionService.fetchItemMap(product, pc)
                    def selectedItems = productionService.fetchItemCountMapFromParams(params)
                    def msg = message(code:'productionJob.created', args:[message(code:product.name)])
                    log.debug("message: $msg")
                    def jobCount = pc.productionJobs.size()
                    return render(template:'/production/selectItems',
                            model:[product:product,
                                    itemMap:itemMap,
                                    selectedItems:selectedItems,
                                    prodStarted:msg,
                                    jobCount:jobCount
                            ])
                }
                catch(RuntimeException ex){
                    log.debug("failed to create a new ProductionJob", ex)
                    return render(status:503, text:message(code:ex.getMessage()));
                }
            }
            else{
                return render(status:503, text:message(code:'error.insufficient.resources'))
            }
        }
        else{
            return render(status:503, text:message(code:'error.missing.product'))
        }
    }

    /**
     * Cancel a ProductionJob. Checks if the job belongs to the active player.
     * Will redirect him to the workshop if no more jobs remain, otherwise
     * redirect back to the list of production jobs.
     */
    @Secured(['ROLE_USER'])
    def cancelProductionJob = {
        def pc = fetchPc()
        if(! pc){
            return redirect(action:'start', controller:'portal')
        }
        def job = ProductionJob.get(params.job)
        if(job){
            if(job.pc.equals(pc)){
                productionService.terminateJob job
                flash.message = message(code:'production.job.canceled')
                if(pc.productionJobs.size() > 0){
                    return redirect(action:'listProductionJobs', controller:'production')
                }
            }
            else{
                flash.message = message(code:'error.object.foreign')
            }
        }
        else{
            flash.message = message(code:'error.job.not_found')
        }
        return redirect(action:'workshop', controller:'production')
    }


}
