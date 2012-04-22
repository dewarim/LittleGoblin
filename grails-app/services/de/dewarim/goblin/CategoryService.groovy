package de.dewarim.goblin

import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.shop.ShopCategory
import de.dewarim.goblin.item.ItemCategory

/**
 *
 */
class CategoryService {

    /**
     * Generate a list of all shops that are associated with this category.
     * @param cat category whose shops you want listed
     * @return a list of shops which are connected with this category
     */
    List<Shop> fetchSelectedShops(Category cat) {
        return cat.shopCategories*.shop
    }

    void updateShopCategories(Category cat, shops) {
        // delete no longer wanted shop-categories:
        cat.shopCategories.each {ShopCategory sc ->
            if (!shops.find {it == sc.shop}) {
                sc.deleteFully()
            }
        }
        // add new ones:
        shops.each {Shop shop ->
            ShopCategory sc = ShopCategory.findByShopAndCategory(shop, cat)
            if (sc == null) {
                sc = new ShopCategory(shop, cat)
                sc.save()
            }
        }
    }

    /**
     * Generate a list of all item types that are associated with this category.
     * @param cat category whose shops you want listed
     * @return a list of item types which are connected with this category
     */
    List<ItemType> fetchSelectedItemTypes(Category cat) {
        return cat.itemCategories*.itemType
    }

    void updateItemCategories(Category cat, items) {
        // delete no longer wanted shop-categories:
        cat.itemCategories.each {ItemCategory ic ->
            if (!items.find {it == ic.itemType}) {
                ic.deleteFully()
            }
        }
        // add new ones:
        items.each {ItemType itemType ->
            ItemCategory ic = ItemCategory.findByItemTypeAndCategory(itemType, cat)
            if (ic == null) {
                ic = new ItemCategory(itemType, cat)
                ic.save()
            }
        }

    }


}
