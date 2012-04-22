package de.dewarim.goblin

import de.dewarim.goblin.item.ItemType;

/**
 * Mapping class between ItemType and EquipmentSlotType.
 * Instances of this class define which EquipmentSlots an Item needs to
 * be usable by a player character.
 * An item may require several types of EquipmentSlots.
 * For example, a 2-handed broadsword will require 2 hands.
 * But this mechanic also may be used for other interesting stuff,
 * like a full suit of rune armor which may only be worn once a complete set has been found.
 * Or a runic woad-skin-painting, which prevents the user from carrying any other
 * kind of armor (ever again).
 *
 * Note: (itemType : slotType) is not required to be unique, you may
 * map itemType[longSword] to slotType[hand] twice.
 *
 */
class RequiredSlot {

    static belongsTo = [itemType:ItemType, slotType:EquipmentSlotType]
	Integer amount = 1

    RequiredSlot() {
    }

    /**
     * Create a new RequiredSlot instance and add it to the relation instances
     * (ItemType.requiredSlots and EquipmentSlotType.requiredSlots)
     * @param slotType the equipment slot type
     * @param itemType the item type
     */
    RequiredSlot(EquipmentSlotType slotType, ItemType itemType) {
        this.slotType = slotType
        this.itemType = itemType
        slotType.addToRequiredSlots this
        itemType.addToRequiredSlots this
    }

}
