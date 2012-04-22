package de.dewarim.goblin.item

import de.dewarim.goblin.UserProperty

class ItemAttribute {
	
	static hasMany = [conditions:ItemAttributeCondition]
	UserProperty affectedProperty

}
