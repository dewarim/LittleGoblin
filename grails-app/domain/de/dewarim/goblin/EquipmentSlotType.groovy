package de.dewarim.goblin

import de.dewarim.goblin.item.ItemType;

class EquipmentSlotType {

    /*
     * Items have requiredSlots, creatures have equipmentSlots.
     * For an item to be usable, the creature needs as many free equipment slots as the item has required slots.
     */
    static hasMany = [requiredSlots:RequiredSlot, equipmentSlots:EquipmentSlot]
	static constraints = {
		name(blank:false, nullable:false)
	}
	
	String name
	
}
