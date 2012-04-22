package de.dewarim.goblin.item

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.ItemLocation

/**
 *
 */
class PostOfficeService {

    Integer computeTransferCost(Integer amount, Item item){
        def cost = (int) (item.type.baseValue * amount / 10)
        if(cost == 0){
            return 1
        }
        return cost
    }

    void sendItem(Integer amount, Item item, PlayerCharacter sender, PlayerCharacter recipient){
        item.owner = recipient
        if(sender != recipient){
            if(amount >= item.amount){
                // transfer the ownership completely:
                sender.items.remove(item)
                recipient.items.add(item)
            }
            else{
                // partial transfer: create new item:
                Item parts = new Item(item.type, recipient)
                parts.save()
                item.amount = item.amount - amount
                /*
                 * Note: this code does not handle stackable items with less than max uses.
                 * But then those items should not exist in the first place.
                 */
            }
        }
        item.location = ItemLocation.AT_HOME
        sender.gold = sender.gold - computeTransferCost(amount, item)
        if(sender.gold < 0){
            // the calling code should prevent this from happening, but the following should
            // help prevent negative amounts of gold:
            log.warn("PostOfficeService encountered a negative amount of gold. Check calling code.")
            sender.gold = 0
        }
    }

}
