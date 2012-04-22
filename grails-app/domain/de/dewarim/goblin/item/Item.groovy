package de.dewarim.goblin.item

import de.dewarim.goblin.Creature
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.ItemLocation
import de.dewarim.goblin.pc.crafting.ProductionResource

class Item {
	/**
	 * attributes: passive effects like resist fire, see invisible.
	 * features: active effects for which you must use an item, like heal self.
	 */
    static hasMany = [resources:ProductionResource]
	 //	String name
	Integer uses = 1
	Boolean equipped = false
    Integer amount = 1 // if the itemType is stackable, amount may be larger than 1
	Long goldValue = 0
    ItemLocation location = ItemLocation.ON_PERSON
    ItemType type
    Creature owner

    Item(){}

    Item (ItemType itemType, PlayerCharacter pc){
        this.type = itemType
        this.owner = pc
//		type.addToItems(this)
        initItem()
    }
        
	void initItem(){
        if(type.usable){
			uses = type.uses
		}
		goldValue = type.baseValue
	}

    void initItem(Integer amount){
        initItem()
        if(type.stackable){
			this.amount = amount
		}
	}


	
	// Note: hashCode & equals assume that there is a field long id.
	@Override
	public int hashCode() {
		if(id == null){
			id = 0
		}
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

    @Override
    boolean equals(o) {
        if (this.is(o)) return true;
        if (getClass() != o.class) return false;
        Item item = (Item) o;
        if (id != item.id) return false;
        return true;
    }

}
