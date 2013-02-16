package de.dewarim.goblin

class EquipmentSlotType {

    /*
     * Items have requiredSlots, creatures have equipmentSlots.
     * For an item to be usable, the creature needs as many free equipment slots as the item has required slots.
     */
    static hasMany = [requiredSlots:RequiredSlot, equipmentSlots:EquipmentSlot]
	static constraints = {
		name(blank:false)
	}

	String name

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof EquipmentSlotType)) return false

        EquipmentSlotType that = o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}
