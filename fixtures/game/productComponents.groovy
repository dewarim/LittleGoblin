import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.crafting.Component

include('game/products')

def iron = ItemType.findByName('item.iron.bar')
def sword = ItemType.findByName('item.weapon.sword')
def shield = ItemType.findByName('item.armor.shield')

fixture{
    
    oreComponent(Component, itemType:ItemType.findByName('item.iron.ore'), amount:3, 
            product:ironBarProd, type: ComponentType.INPUT)
    ironComponent(Component, itemType: iron, amount:1, 
            product:ironBarProd, type:ComponentType.OUTPUT)
    
    swordInComponent(Component, itemType:iron, amount:3, product:swordProd, type: ComponentType.INPUT)
    swordComponent(Component, itemType: sword, amount:1, product:swordProd, type: ComponentType.OUTPUT)
    
    shieldInComponent(Component, itemType:iron, amount:3, product:shieldProd, type: ComponentType.INPUT)
    shieldComponent(Component, itemType:shield, amount:1, product:shieldProd, type: ComponentType.OUTPUT)
}