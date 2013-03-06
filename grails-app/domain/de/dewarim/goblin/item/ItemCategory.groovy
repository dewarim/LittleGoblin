package de.dewarim.goblin.item

import de.dewarim.goblin.Category

/**
 * Mapping class between ItemType and Category
 */
class ItemCategory {

    static belongsTo = [itemType: ItemType, category: Category]

    ItemCategory() {
    }

    ItemCategory(ItemType type, Category category) {
        this.itemType = type
        this.category = category
        type.addToItemCategories this
        category.addToItemCategories this
    }

    void deleteFully() {
        itemType.removeFromItemCategories this
        category.removeFromItemCategories this
        delete()
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ItemCategory)) return false

        ItemCategory that = o

        if (category != that.category) return false
        if (itemType != that.itemType) return false

        return true
    }

    int hashCode() {
        int result
        result = (category != null ? category.hashCode() : 0)
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0)
        return result
    }
}
