package de.dewarim.goblin.item

import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.combat.WeaponAttribute
import de.dewarim.goblin.Dice

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

    // option: maxStackSize
    // option: weight / encumbrance
    // option: durability (resistance to damage)

    List getItems() {
        return Item.findAll("from Item as i where i.type=:type", [type: this])
    }
}
