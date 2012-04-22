package de.dewarim.goblin;

import de.dewarim.goblin.item.Item;
import de.dewarim.goblin.item.ItemType;
import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.pc.PlayerCharacter;

/**
 * Purpose: based on a monster, create a list of items found on it.
 * Version 1: just return item #1 
 * 
 * @author ingo
 *
 */
class TreasureService {
	
	List<Item> findTreasure(Mob mob, PlayerCharacter pc){
//		def type = ItemType.findByName('Potion of Healing')
//		def type = ItemType.findByName('Leather Cap')
		def types = ItemType.list()
		def type = types.get( (int) (Math.random() * types.size()) )
        Item item = new Item(type:type, owner:pc)
        item.save()
		return [item]
	}
	
}
