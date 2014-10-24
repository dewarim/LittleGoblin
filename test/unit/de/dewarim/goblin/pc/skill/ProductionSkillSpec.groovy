package de.dewarim.goblin.pc.skill

import de.dewarim.ConstraintUnitSpec
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.PlayerProduct
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * Test specification for class ProductionSkill.
 * Note: basic tests for its abstract parent class "Skill" can be found in CombatSkillSpec.
 */
@TestFor(ProductionSkill)
@Mock([ProductionSkill, SkillRequirement, UserAccount, CreatureSkill, PlayerProduct, PlayerCharacter, Product ])
class ProductionSkillSpec extends ConstraintUnitSpec {
    
    void setup(){

    }
    
    void "initSkillTest"(){
        given:
        def user = new UserAccount(username: "Bob's Uncle")
        def pc = new PlayerCharacter(name:"Bob", user:user)
        pc.save()
        def product = new Product(name: "acme", 
                category: new ProductCategory(name: "everything"),
                timeNeeded: 1
        )
        product.save()
        def skill = new ProductionSkill(name: 'acme.maker.skill')
        def skillRequirement = new SkillRequirement(skill:skill, product: product)
        skill.addToSkillRequirements(skillRequirement)
        product.addToRequiredSkills(skillRequirement)
        pc.addToCreatureSkills(new CreatureSkill(owner: pc, skill:skill))
        skillRequirement.save()
        
        when:
        skill.initSkill(pc)
        
        then:
        PlayerProduct.findAllWhere([product: product, pc:pc]).size() == 1
        pc.playerProducts.contains(PlayerProduct.findByProduct(product))
    }

}
