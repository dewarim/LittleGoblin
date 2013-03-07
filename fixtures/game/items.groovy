import de.dewarim.goblin.item.ItemCategory
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.item.ItemTypeFeature

include('game/categories')
include('game/features')

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

}