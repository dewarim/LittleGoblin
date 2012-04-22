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
}
