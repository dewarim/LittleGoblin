package de.dewarim.goblin.item

import de.dewarim.goblin.ItemLocation
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.ProductionResource

class Item {
    /**
     * attributes: passive effects like resist fire, see invisible.
     * features: active effects for which you must use an item, like heal self.
     */
    Integer uses = 1
    Boolean equipped = false
    Integer amount = 1 // if the itemType is stackable, amount may be larger than 1
    Long goldValue = 0
    ItemLocation location = ItemLocation.ON_PERSON
    ItemType type
    PlayerCharacter owner

    Item() {}

    Item(ItemType itemType, PlayerCharacter pc) {
        this.type = itemType
        this.owner = pc
        initItem()
    }

    void initItem() {
        if (type.usable) {
            uses = type.uses
        }
        goldValue = type.baseValue
    }

    void initItem(Integer amount) {
        initItem()
        if (type.stackable) {
            this.amount = amount
        }
    }
    
    List<ProductionResource> getResources(){
        return ProductionResource.findAllWhere([item: this])
    }
    
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Item)) return false

        Item item = o

        if (amount != item.amount) return false
        if (equipped != item.equipped) return false
        if (goldValue != item.goldValue) return false
        if (location != item.location) return false
        if (owner != item.owner) return false
        if (type != item.type) return false
        if (uses != item.uses) return false

        return true
    }

    int hashCode() {
        int result
        result = (uses != null ? uses.hashCode() : 0)
        result = 31 * result + (equipped != null ? equipped.hashCode() : 0)
        result = 31 * result + (amount != null ? amount.hashCode() : 0)
        result = 31 * result + (goldValue != null ? goldValue.hashCode() : 0)
        result = 31 * result + (location != null ? location.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        result = 31 * result + (owner != null ? owner.hashCode() : 0)
        return result
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", uses=" + uses +
                ", equipped=" + equipped +
                ", amount=" + amount +
                ", goldValue=" + goldValue +
                ", location=" + location +
                ", type=" + type.name +
                ", typeId=" + type.id +
                ", owner=" + owner.name +
                '}';
    }
}
