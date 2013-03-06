package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.item.ItemType

/**
 *
 */
class Component {
    // basically, this is a Product-ItemType- 1..n join-class.

    ItemType itemType
    Integer amount = 1
    ComponentType type
    Component product
    
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Component)) return false

        Component component = o

        if (amount != component.amount) return false
        if (itemType != component.itemType) return false
        if (product != component.product) return false
        if (type != component.type) return false

        return true
    }

    int hashCode() {
        int result
        result = (itemType != null ? itemType.hashCode() : 0)
        result = 31 * result + (amount != null ? amount.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        result = 31 * result + (product != null ? product.hashCode() : 0)
        return result
    }
}
