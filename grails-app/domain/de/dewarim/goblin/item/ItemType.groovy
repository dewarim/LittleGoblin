package de.dewarim.goblin.item

import de.dewarim.goblin.Dice
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.combat.WeaponAttribute

class ItemType {

    static hasMany = [
            requiredSlots: RequiredSlot,
            attributes: ItemAttribute,
            combatAttributes: WeaponAttribute,
            resistanceAttributes: WeaponAttribute,
            itemTypeFeatures: ItemTypeFeature,
            itemCategories: ItemCategory           
    ]

    static constraints = {
        name unique: true
        combatDice nullable: true
    }

    String name
    String description = ''
    Boolean usable = false
    Boolean rechargeable = false
    Integer uses = 1
    Integer baseValue = 0
    Integer availability = 500 // 1000 = 100%, 0 = 0% change of seeing this in a shop.
    Dice combatDice
    Boolean stackable = false
    Integer packageSize = 1 // If you buy an item of this type, how many pieces do you get?
    Boolean active = true

    // option: maxStackSize
    // option: weight / encumbrance
    // option: durability (resistance to damage)

    List getItems() {
        return Item.findAll("from Item as i where i.type=:type", [type: this])
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ItemType)) return false

        ItemType itemType = o

        if (availability != itemType.availability) return false
        if (baseValue != itemType.baseValue) return false
        if (combatDice != itemType.combatDice) return false
        if (description != itemType.description) return false
        if (name != itemType.name) return false
        if (packageSize != itemType.packageSize) return false
        if (rechargeable != itemType.rechargeable) return false
        if (stackable != itemType.stackable) return false
        if (usable != itemType.usable) return false
        if (uses != itemType.uses) return false
        if (active != itemType.active) return false
        return true
    }

    int hashCode() {
        return name.hashCode()
    }
}
