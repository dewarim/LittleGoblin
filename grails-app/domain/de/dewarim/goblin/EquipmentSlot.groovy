package de.dewarim.goblin;

import de.dewarim.goblin.item.Item;

/**
 * Mapping class between EquipmentSlotType and Creature.
 * A creature usually has several equipment slots which are required to
 * wield and carry items like armor, weapons or amulets. (Or: weapon bays
 * to carry lasers, machine guns, missiles).
 */
class EquipmentSlot {
	
	static belongsTo = [type:EquipmentSlotType, creature:Creature]
	static constraints = {
		name(blank:false,nullable:false)
		item(nullable:true)
	}
	
	String name // name like: "left hand" "right hand" "head 2"
	Item item
	Integer rank // for sorting the slots.

}
