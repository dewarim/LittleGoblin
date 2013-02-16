package de.dewarim.goblin.shop

import de.dewarim.goblin.Category

/**
 * Mapping class between Shop and Category
 */
class ShopCategory {

    static belongsTo = [shop:Shop, category:Category]

    ShopCategory() {
    }

    ShopCategory(Shop shop, Category category) {
        this.shop = shop
        this.category = category
        shop.addToShopCategories this
        category.addToShopCategories this
    }


    void deleteFully(){
        shop.removeFromShopCategories this
        category.removeFromShopCategories this
        delete()
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ShopCategory)) return false

        ShopCategory that = o

        if (category != that.category) return false
        if (shop != that.shop) return false

        return true
    }

    int hashCode() {
        int result
        result = (shop != null ? shop.hashCode() : 0)
        result = 31 * result + (category != null ? category.hashCode() : 0)
        return result
    }
}
