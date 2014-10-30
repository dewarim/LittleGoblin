package de.dewarim.goblin

import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductionJob
import de.dewarim.goblin.pc.crafting.ProductionResource
import de.dewarim.goblin.ticks.ITickListener

class ProductionService implements ITickListener{

    def itemService

    /**
     * Compute how many items of this product the PC can produce.
     * @param product the product to make
     * @param pc the player character who wants to craft
     * @return the maximum amount of items the player can create given his
     * current resources.
     */
    Integer computeMaxProduction(Product product, PlayerCharacter pc){
        Integer maxProduction = Product.MAX_ITEMS_PER_RUN
        product.fetchInputItems().each{component ->
            def type = component.itemType
            def maxOfThisType = pc.calculateSumOfItems(type) / component.amount
            if(maxProduction > maxOfThisType){
                maxProduction = maxOfThisType
            }
        }
        return maxProduction
    }

    /**
     * Go through the player character's items and collect a map(component,List::item) of those items
     * which may be used to create the product.
     * This method is used to create the selectComponents view of the ProductionController
     * @param product the product the player wishes to create
     * @param pc the active player character
     * @return a map of items that this PC owns which may be used to create the product
     */
    Map fetchItemMap(Product product, PlayerCharacter pc){
        Map<Component, List<Item>> itemMap = [:]
        log.debug("inputItems: ${product.fetchInputItems()?.size()}")
        product.fetchInputItems().each{component->
            def myItems = Item.findAll("from Item i where i.owner=:owner and i.type=:type", [owner:pc, type:component.itemType])
            log.debug("myItems: ${myItems?.dump()}")
            myItems.each{item ->
                if(itemMap.get(component)){
                    itemMap.get(component).add(item)
                }
                else{
                    itemMap.put(component, [item])
                }
            }
        }
        return itemMap
    }
    /**
     * Check if a player has selected enough resources to create a given product. This does not check
     * if the resources are actually available at this point. The player sort of makes a promise:
     * "I will have the required resources later on, and I want to use the following piles of items
     * I have here." If the promise is believable (that is, if the ItemTypes are correct and the total
     * amount promised is at least equal to the amount of required resources), a ProductionJob may be
     * created.
     * @param product the product the player wants to create
     * @param pc the player character
     * @param params the http request params
     * @return true if enough resources have been selected, false otherwise
     */
    boolean enoughResourcesSelected(Product product, PlayerCharacter pc, params){
        log.debug("received params: ${params}")

         // itemTypeMap is a map [itemType : itemCount]
        Map itemTypeMap = fetchItemCountMapFromParams(params)
        log.debug("itemMap: ${itemTypeMap.dump()}")
        boolean enough = true
        product.fetchInputItems().each{component ->
            ItemType iType = component.itemType
            if(itemTypeMap.containsKey(iType)){
                if(itemTypeMap.get(iType) < component.amount){
                    enough = false
                }
            }
            else{
                enough = false
            }
        }

        return enough
    }

    /**
     * Turn the item ids in the HTTP request into a map of [itemType::amount]
     * @param params the http request parameters
     * @return a map of itemType::amount
     */
    Map fetchItemCountMapFromParams(params){
        def itemTypeMap = [:]
        def itemList = extractItemListFromParams(params)
        log.debug("ItemList found in params with ${itemList?.size()} entries.")
        itemList.each {item ->
            try{
                log.debug("item")
                def amount = params["item_${item.id}"]?.toLong()
                if (amount) {
                    def type = item.type
                    if (itemTypeMap.containsKey(type)) {
                        itemTypeMap.put(type, itemTypeMap.get(type) + amount)
                    }
                    else{
                        itemTypeMap.put(type, amount)
                    }
                }
            }
            catch(NumberFormatException nfe){
                log.debug("Encountered illegal item id in params: "+params["item_${item.id}"])
            }
        }
        return itemTypeMap
    }

    /**
     * An item has an html input field with name item_$id. This method extracts
     * the id, loads the item and puts it into a list.
     * @param params the http request params
     * @return a list of items
     */
    List<Item> extractItemListFromParams(params){
        List<Item> itemList = []

        def items = params.findAll{it.key.startsWith('item_')}.collect{it.key}
        items.each{ itemParam ->
            itemParam = itemParam - 'item_'
            log.debug("found item param: ${itemParam}")
            if (! itemParam) {
                return // in case the item param is invalid
            }

            Item item = Item.get(itemParam.toLong())
            if (item) {
                itemList.add(item)
            }
        }
        return itemList
    }

    /**
     * Create a new ProductionJob for the selected product. Verify that
     * the correct amount of items has been selected.
     * @param product the product that the player wants to create
     * @param pc the player character
     * @param params http request params
     */
    void createNewProductionJob(Product product, PlayerCharacter pc, params){
        if(enoughResourcesSelected(product, pc, params)){
             // check that the items selected really belong to this player
            def items = extractItemListFromParams(params)
            if(items.find{! it.owner.equals(pc)}){
                throw new RuntimeException('production.foreign.item')
            }
            def prodJob = new ProductionJob(pc:pc, product:product)
            items.each{item ->
                def amount = Integer.parseInt(params["item_${item.id}"])
                def resource = new ProductionResource(job:prodJob, item:item, amount:amount)
                prodJob.addToResources(resource)
            }
            prodJob.finished = new Date( new Date().getTime() + product.timeNeeded)
            prodJob.save()
            log.debug("created ProductionJob ${prodJob.id}")
        }
        else{
            throw new RuntimeException('production.missing.resources')
        }
    }

    /**
     * Go through the list of ProductionJobs and try to create each product if the
     * PC has the required amount of products ready. Otherwise, put his production on hold
     * for some time.
     * Note: I want a player to be able to create a list of production jobs, and each
     * of them should be able to create items that will be used "further down the line".
     * But what if the list of ProductionJobs is clogged with jobs that cannot be completed,
     * because the player has left the game or has just put hundreds of fake jobs on the line?
     * Thus a ProductionJob has a limited TTL (Time-To-Live). It will be bumped back in the
     * line until it can be completed - or until its TTL is reached. This way, a player may
     * create a ProductionJob to be done with resources that are not available at this very
     * moment but may be there soon (for example, components which will be produced over night).
     * This of course only goes so far - the player has to decide on an actual item to use,
     * for example: "I want to use 20 of those gold ingots to create a golden grail. No matter
     * that I only got 10 at the moment, but later on I will have all 20 after my 'create gold
     * ingot from ore' production job is through." Note that the player should not be able to
     * submit a job where he has not selected a resource. The program cannot guess if he wanted
     * to enchant one of 99 arrows or his big bad sword to create an expensive rune weapon.
     * Todo: this text should go into the wiki. Once we have one.
     * Todo: multiple production lines. Crossover between PLs
     * @return the number of processed production jobs.
     */
    Integer makeProducts(){
        int productCount = 0
        def jobs =  ProductionJob.findAll("from ProductionJob as pj where pj.finished < now()")
        log.debug("found ${jobs.size()} production jobs")
        jobs.each{job ->
            // check if sufficient resources are available
            if(checkResources(job)){
                log.debug("spend resources")
                spendResources(job)
                log.debug("create product")
                createProduct(job)
                log.debug("created product")
            }
            else{
                log.debug("not enough resources")
                job.ttl--
                if(job.alive()){
                    job.postpone()
                }
                else{
                    terminateJob(job)
                }
            }
            productCount++
        }
        return productCount
    }

    /**
     * Check if the player has enough resources to pay for the production of an item.
     * This method examines each resource and makes sure that
     * <ul>
     * <li>the player is still the owner of the previously selected item</li>
     * <li>the player has a sufficient amount of this item in stock.</li>
     * </ul>
     * @param job
     * @return
     */
    boolean checkResources(ProductionJob job){
        for(resource in job.resources){
//            log.debug("items: ${resource.item.type.name} ${resource.item.amount} (needed: ${resource.amount})")
            if( (resource.item.owner != job.pc) || // item has to be still in the player's possession
                    (resource.amount > resource.item.amount) ){
                return false
            }
        }
        return true
    }

    /**
     * Spend the required resources on a production job. If an item is depleted (that is,
     * there are not more of it around), it will be deleted.
     * @param job
     */
    void spendResources(ProductionJob job){
        Set<ProductionResource> resources = job.resources
        log.debug("job needs ${resources?.size()} resources.")
        resources.each{resource ->
            Item item = resource.item
            PlayerCharacter pc = job.pc
            item.amount -= resource.amount
            job.removeFromResources resource
            item.removeFromResources resource
            resource.delete(flush:true)

//            if(item.amount <= 0){
//                // TODO: there may be a race condition here, so we use <= instead of ==
//                // (perhaps run this inside a transaction?)
//                // delete item:
//                item.type.removeFromItems item
//                pc.removeFromItems item
//                item.delete()
//            }
        }
    }

    /**
     * Create the items which make up this product and delete
     * the ProductionJob afterwards.
     * TODO: send a message to the player telling him that the job is done.
     * @param job the ProductionJob which describes the product and who will get the resulting items.
     */
    void createProduct(ProductionJob job){
        Product product = job.product
        product.fetchOutputItems().each{component ->
            ItemType type = component.itemType
            def pc = job.pc
            // check if player already has one of this item:
            Item existingItem = pc.items.find{it.type == type}
            if(type.stackable && existingItem){
                existingItem.amount++
            }
            else{
                // TODO: find newest asset version of item.
                Item item = new Item(type:type, owner:job.pc, amount:component.amount)
//                pc.addToItems(item)
                item.save()
            }
        }
        job.amount--
        if(job.amount == 0){
            terminateJob(job)
        }
        else{
            job.continueJob()
        }
    }

    /**
     * Terminate a ProductionJob, either because the product is finished or because the
     * player character has not been able to create this item.
     * @param job the ProductionJob that will be deleted.
     */
    void terminateJob(ProductionJob job){
        job.pc.removeFromProductionJobs job
        job.delete()
    }

    void tock(){
        Integer amount = makeProducts()
        itemService.cleanupItems()
        log.debug("created $amount items.")
    }
}
