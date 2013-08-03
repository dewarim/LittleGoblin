package de.dewarim.goblin.shop

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.town.Town

class Shop {

	static hasMany = [itemTypes:ItemType, shopCategories:ShopCategory]
	static belongsTo = [owner:ShopOwner, town:Town]
	static constraints = {
		name blank:false
		description nullable:true
	}

	/**
	 * Name of the Shop
	 */
	String name
	String description

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
}
