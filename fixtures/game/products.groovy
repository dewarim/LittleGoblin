import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.SkillRequirement

include('game/productCategories')
include('game/productionSkills')

fixture{
    
    ironBarProd(Product, name:'product.iron.bar', timeNeeded:1000, category:basicCat)
    swordProd(Product, name:'product.weapon.sword', timeNeeded: 1000, category:weaponCat)
    shieldProd(Product, name:'product.armor.shield', timeNeeded: 1000, category: armorCat)
    
    craftSword(SkillRequirement, product:swordProd, skill:psSword)
    craftArmor(SkillRequirement, product: shieldProd, skill:psShield)
    craftIron(SkillRequirement, product:ironBarProd, skill:psIron)
}