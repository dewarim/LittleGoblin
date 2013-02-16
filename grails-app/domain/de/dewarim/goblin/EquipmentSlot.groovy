package de.dewarim.goblin

import de.dewarim.goblin.item.Item

/**
 * Mapping class between EquipmentSlotType and Creature.
 * A creature usually has several equipment slots which are required to
 * wield and carry items like armor, weapons or amulets. (Or: weapon bays
 * to carry lasers, machine guns, missiles).
 */
class EquipmentSlot {

	static belongsTo = [type:EquipmentSlotType, creature:Creature]
	static constraints = {
		name(blank:false)
		item(nullable:true)
	}

	String name // name like: "left hand" "right hand" "head 2"
	Item item
	Integer rank // for sorting the slots.
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof EquipmentSlot)) return false

        EquipmentSlot that = o

        if (creature != that.creature) return false
        if (item != that.item) return false
        if (name != that.name) return false
        if (rank != that.rank) return false
        if (type != that.type) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (item != null ? item.hashCode() : 0)
        result = 31 * result + (rank != null ? rank.hashCode() : 0)
        result = 31 * result + (creature != null ? creature.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        return result
    }
}
