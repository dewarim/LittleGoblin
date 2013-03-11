import de.dewarim.goblin.Dice
import de.dewarim.goblin.EquipmentSlotType
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.combat.WeaponAttribute
import de.dewarim.goblin.item.ItemCategory
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.ItemTypeFeature

include('game/categories')
include('game/features')

def findSlotType(String name){
    return EquipmentSlotType.findByName(name)
}

Dice findDie(String name){
    return Dice.findByName(name)
}

CombatAttributeType findCat(String name){
    return CombatAttributeType.findByName(name)
}

def hand = findSlotType('slot.hand')
def normalDam =  findCat('attribute.normal')

fixture {
    healingPotion(ItemType, name: 'item.healing_potion',
            usable: true, uses: 1, availability: 999, baseValue: 5)
    potionIc(ItemCategory, itemType: healingPotion, category: potion)
    magicIc(ItemCategory, itemType: healingPotion, category: magic)
    healingPotionFeature(ItemTypeFeature, itemType: healingPotion, feature: healing1W6)

    redShroom(ItemType, name: 'item.mushroom.red', availability: 0, baseValue: 2)
    redMushroomIc(ItemCategory, itemType: redShroom, category: mushroom)
    blackShroom(ItemType, name: 'item.mushroom.black', availability: 0, baseValue: 2)
    blackMushroomIc(ItemCategory, itemType: blackShroom, category: mushroom)

    ore(ItemType, name: 'item.iron.ore', availability: 900, baseValue: 10, stackable: true)
    oreIc(ItemCategory, itemType: ore, category: metal)
    iron(ItemType, name: 'item.iron.bar', availability: 900, baseValue: 40, stackable: true)
    ironIc(ItemCategory, itemType: iron, category: metal)

    philStone(ItemType, name: 'item.philosopher_stone', availability: 0, baseValue: 43)
    sword(ItemType, name: 'item.weapon.sword', availability: 140, baseValue: 20)
    swordIc(ItemCategory, itemType: sword, category: weapon)
    shield(ItemType, name: 'item.armor.shield', availability: 140, baseValue: 20)
    shieldIc(ItemCategory, itemType: shield, category: armor)
    shieldSlot(RequiredSlot, itemType: shield, slotType: hand)
    
    clothArmor(ItemType, name:'armor.cloth', baseValue: 4)
    clothSlot(RequiredSlot, itemType:clothArmor, slotType: findSlotType('slot.body'))
    
    leatherCap(ItemType, name: 'armor.leather_cap', baseValue: 4)
    capSlot(RequiredSlot, itemType: leatherCap, slotType:findSlotType('slot.head'))
    
    fireWhip(ItemType,name: 'weapon.fire_whip', availability: 1000,
            baseValue: 50, combatDice: findDie('2d6+2') )
    whipAttr(WeaponAttribute, itemType:fireWhip, damageModifier:2.0, combatAttributeType:findCat('attribute.fire'))
    whipSlot(RequiredSlot, itemType: fireWhip, slotType: hand)
    
    shortSword(ItemType, name: 'weapon.short_sword', availability: 750,
            baseValue: 5, combatDice: findDie('d6'))
    swordAttr(WeaponAttribute, itemType: shortSword,damageModifier: 1.0, combatAttributeType: normalDam)
    swordSlot(RequiredSlot, itemType: shortSword, slotType: hand) 
    
    longSword(ItemType, name: 'weapon.short_sword', availability: 750,
            baseValue: 30, combatDice: findDie('2d6'))
    longSwordAttr(WeaponAttribute, itemType:longSword, damageModifier: 1.0, combatAttributeType: normalDam)
    longSwordSlot1(RequiredSlot, itemType: longSword, slotType: hand) // Long sword requires 2 slots. 
    longSwordSlot2(RequiredSlot, itemType: longSword, slotType: hand)
    
    staff(ItemType, name: 'weapon.long_staff', availability: 350,
            baseValue: 23, combatDice: findDie('2d5+2'))
    staffAttr(WeaponAttribute, itemType:staff, damageModifier: 1.0, combatAttributeType: normalDam)
    staffSlot1(RequiredSlot, itemType: staff, slotType: hand)
    staffSlot2(RequiredSlot, itemType: staff, slotType: hand)
    
    pick(ItemType, name: 'weapon.toothpick_of_death', availability: 500,
            baseValue: 10, combatDice: findDie('d6'))
    pickAttr(WeaponAttribute, itemType:pick, damageModifier: 2, combatAttributeType: findCat('attribute.death'))
    pickSlot(RequiredSlot, itemType: pick, slotType: hand)
    
    torch(ItemType, name: 'weapon.torch', availability: 1000,
            baseValue: 1, combatDice: findDie('1d4'))
    torchAttr(WeaponAttribute, itemType:torch, damageModifier: 1.25, combatAttributeType: findCat('attribute.fire'))
    torchSlot(RequiredSlot, itemType: torch, slotType: hand)
    
    [fireWhip, shortSword, longSword, staff, pick, torch ].each{w ->
        "${w.name.replaceAll('\\.','_')}"(ItemCategory, itemType:w, category:weapon)
    }
    whipIc(ItemCategory, itemType:fireWhip, category: magic)
    pickIc(ItemCategory, itemType:pick, category: magic)
}