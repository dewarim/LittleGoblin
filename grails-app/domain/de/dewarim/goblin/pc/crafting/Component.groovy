package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.ComponentType

/**
 *
 */
class Component {
    // basically, this is a Product-ItemType- 1..n join-class.

    static belongsTo = [product:Product]

    ItemType itemType
    Integer amount = 1
    ComponentType type

}
