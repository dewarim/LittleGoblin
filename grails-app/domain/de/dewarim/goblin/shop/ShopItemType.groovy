package de.dewarim.goblin.shop

import de.dewarim.goblin.item.ItemType

class ShopItemType {
    
    static constraints = {
        shop unique: 'itemType'
        version:false
    }
    
    Shop shop
    ItemType itemType

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ShopItemType)) return false

        ShopItemType that = (ShopItemType) o

        if (itemType != that.itemType) return false
        if (shop != that.shop) return false

        return true
    }

    int hashCode() {
        int result
        result = shop.hashCode()
        result = 31 * result + itemType.hashCode()
        return result
    }
}
