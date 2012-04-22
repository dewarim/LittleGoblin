package de.dewarim.goblin.shop;

import de.dewarim.goblin.town.Town
import de.dewarim.goblin.item.ItemType

class Shop {
	
	static hasMany = [itemTypes:ItemType, shopCategories:ShopCategory]
	static belongsTo = [owner:ShopOwner, town:Town]
	static constraints = {
		name blank:false, nullable:false
		description nullable:true
	}

	/**
	 * Name of the Shop
	 */
	String name
	String description
	
}
