package de.dewarim.goblin.item

import de.dewarim.goblin.Dice
import de.dewarim.goblin.asset.IAsset
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.combat.WeaponAttribute

class ItemType implements IAsset {

    static hasMany = [
            requiredSlots: RequiredSlot,
            attributes: ItemAttribute,
            combatAttributes: WeaponAttribute,
            resistanceAttributes: WeaponAttribute,
            itemTypeFeatures: ItemTypeFeature,
            itemCategories: ItemCategory           
    ]

    static constraints = {
//        name unique: 'assetVersion'
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
    Long assetVersion = 1
    String uuid = UUID.randomUUID().toString()
    
    /**
     * If an ItemType is inactive (active==false), it should not be used by the application.
     * ItemTypes are activated through the AssetManager. 
     * The default for active is "true" so we can create items by script or at startup.
     */
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
        
        if (assetVersion != itemType.assetVersion) return false
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
    
    Long getMyId(){
        return id
    }

    boolean isActive() {
        return active
    }


    @Override
    public String toString() {
        return "ItemType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", usable=" + usable +
                ", rechargeable=" + rechargeable +
                ", uses=" + uses +
                ", baseValue=" + baseValue +
                ", availability=" + availability +
                ", combatDice=" + combatDice +
                ", stackable=" + stackable +
                ", packageSize=" + packageSize +
                ", assetVersion=" + assetVersion +
                ", uuid='" + uuid + '\'' +
                ", active=" + active +
                '}';
    }
}
