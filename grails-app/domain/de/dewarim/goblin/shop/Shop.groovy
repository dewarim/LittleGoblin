package de.dewarim.goblin.shop

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.town.Town

class Shop {

    static constraints = {
        name blank: false
        description nullable: true
    }

    /**
     * Name of the Shop
     */
    String name
    String description
    ShopOwner owner
    Town town
    
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Shop)) return false

        Shop shop = o

        if (description != shop.description) return false
        if (name != shop.name) return false
        if (owner != shop.owner) return false
        if (town != shop.town) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (town != null ? town.hashCode() : 0)
        return result
    }

    List<ItemType> getItemTypes() {
        return ShopItemType.findAllByShop(this).collect { it.itemType }
    }
    
    List<ShopCategory> getShopCategories(){
        return ShopCategory.findAllByShop(this)
    }
    
}
