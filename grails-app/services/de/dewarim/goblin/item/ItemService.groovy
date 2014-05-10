package de.dewarim.goblin.item

import de.dewarim.goblin.Category
import de.dewarim.goblin.ItemLocation
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.shop.Shop
/**
 * ItemService: provide a list of item types for shops.
 *
 * @author ingo
 *
 */
class ItemService {

	/*
	 * This simple version rolls for each ItemType if it is available.
	 */
	List<ItemType> fetchItemTypes(Shop shop){
        // TODO: only return current asset versions of items.
		return ItemType.list()
                .findAll{ Math.random() < it.availability / 1000 }
                .sort{a,b -> a.name <=> b.name}
	}

    /**
     * To display the items of a shop sorted by their category, we need
     * a map of Category:List(Type)
     * @param itemTypes list of itemTypes
     * @return Map of itemCategory to list of itemTypes.
     */
    Map<ItemCategory, List<ItemType>> fetchItemCategoryTypeMap(Collection<ItemType> itemTypes){
        Map<ItemCategory, List<ItemType>> map = [:]
        itemTypes.each{type ->
            type.itemCategories.each{category ->
                if(map.get(category)){
                    map.get(category).add(type)
                }
                else{
                    map.put(category, [type])
                }
            }
        }
        return map
    }

     /**
      * Search all items for those whose amount is 0 and delete them.
      */
    void cleanupItems(){
        def items = Item.findAll("from Item i where i.amount <= 0", [])
        items.each{item ->
            log.debug("delete item ${item.id} // ${item.type.name}")
//            item.type.removeFromItems item
//            item.owner.removeFromItems item
            item.delete()
        }
    }

    List<ItemType> filterItemTypesByCategory(Collection<ItemType> itemTypes, List<Category> categories){
        if(categories?.isEmpty()){
            return itemTypes.asList()
        }
//        log.debug("filter items by: ${categories*.name}")

        def itemList = []
        itemTypes.each{ItemType type ->
            if(type.itemCategories.collect{it.category}.containsAll(categories)){
                itemList.add(type)
            }
        }
        return itemList
    }

    /**
     * Fetch a list of all usable items a player character has on his person.
     * @param pc the PlayerCharacter whose items you want to list.
     * @return a list of all items of a player which he is carrying, that are usable and have uses left.
     */
    List<Item> fetchUsableItems(PlayerCharacter pc){
        List<Item> items = []
        pc.items.each{item ->
            if(
            item.location.equals(ItemLocation.ON_PERSON) &&
                    item.type.usable &&
                    item.uses > 0 ){
                items.add(item)
            }

        }
        return items
    }

    Item splitItem(Item item, Integer amount){
        Item part = new Item()
        item.amount -= amount
        part.amount = amount
        // TODO: find current itemType.assetVersion?
        part.type = item.type
        part.equipped = false
        part.goldValue = item.type.baseValue
        part.owner = item.owner
        part.location = item.location
//        part.owner.addToItems(part)
        part.save()
        return part
    }

    void combineItems(Item target, Item source){
        if(target.type != source.type){
            return
        }
        if(target.type.stackable){
            target.amount += source.amount
        }
    }


}
