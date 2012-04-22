package de.dewarim.goblin.item

import de.dewarim.goblin.Category

/**
 * Mapping class between ItemType and Category
 */
class ItemCategory {

    static belongsTo = [itemType:ItemType, category:Category]

    ItemCategory() {
    }

    ItemCategory(ItemType type, Category category) {
        this.itemType = type
        this.category = category
        type.addToItemCategories this
        category.addToItemCategories this
    }

    ItemCategory(ItemType type, String categoryName){
        this(type, Category.findByName(categoryName))
    }

    void deleteFully(){
        itemType.removeFromItemCategories this
        category.removeFromItemCategories this
        delete()
    }

}
