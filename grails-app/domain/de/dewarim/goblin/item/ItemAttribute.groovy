package de.dewarim.goblin.item

import de.dewarim.goblin.UserProperty

class ItemAttribute {
	
	static hasMany = [conditions:ItemAttributeCondition]
	UserProperty affectedProperty

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ItemAttribute)) return false

        ItemAttribute that = (ItemAttribute) o

        if (affectedProperty != that.affectedProperty) return false

        return true
    }

    int hashCode() {
        return (affectedProperty != null ? affectedProperty.hashCode() : 0)
    }
}
