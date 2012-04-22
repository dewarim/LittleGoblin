package de.dewarim.goblin

import de.dewarim.goblin.shop.Shop

class ShopService {

    static transactional = false

    List<Category> fetchCategoryList(Shop shop){
        return shop.shopCategories.collect {it.category}
    }
}
