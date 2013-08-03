import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.skill.ProductionSkill

include('game/productCategories')

def psSword = ProductionSkill.findByName('skill.crafting.sword')
def psShield = ProductionSkill.findByName('skill.crafting.shield')
def psIron = ProductionSkill.findByName('skill.crafting.iron')

fixture{
    
    ironBarProd(Product, name:'product.iron.bar', timeNeeded:1000, category:basicCat)
    swordProd(Product, name:'product.weapon.sword', timeNeeded: 1000, category:weaponCat)
    shieldProd(Product, name:'product.armor.shield', timeNeeded: 1000, category: armorCat)
    
    craftSword(SkillRequirement, product:swordProd, skill:psSword)
    craftArmor(SkillRequirement, product: shieldProd, skill:psShield)
    craftIron(SkillRequirement, product:ironBarProd, skill:psIron)
}