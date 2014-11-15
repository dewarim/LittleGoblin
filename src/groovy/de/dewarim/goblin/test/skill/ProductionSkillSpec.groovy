package de.dewarim.goblin.test.skill

import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.PlayerProduct
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.skill.CreatureSkill
import de.dewarim.goblin.pc.skill.ProductionSkill
import spock.lang.Shared
import spock.lang.Specification

/**
 * Test specification for class ProductionSkill.
 * Note: basic tests for its abstract parent class "Skill" can be found in CombatSkillSpec.
 */
class ProductionSkillSpec extends Specification {


    @Shared
    def user = new UserAccount(username: "crafty of prod", passwd: 'foodFood', userRealName: 'none')
    @Shared
    def playerCharacter = new PlayerCharacter(name: "Crafter Prod", user: user)
    @Shared
    def prodCat = new ProductCategory(name: "skill.spec.cat")
    @Shared
    def product  = new Product(name: "Anvil of Injustice", timeNeeded: 1, category: prodCat)
    @Shared
    def productionSkill = new ProductionSkill(name:'Anvil making 101')
    @Shared
    def skillRequirement = new SkillRequirement(skill:  productionSkill, product:  product)
    
    void setupSpec(){
        user.save()
        playerCharacter.save()
        prodCat.save()
        product.save()
        productionSkill.save()
        skillRequirement.save()
        productionSkill.addToSkillRequirements(skillRequirement)
    }
    
    void "initSkillTest"(){
        given:
        def cs = new CreatureSkill(owner: playerCharacter, skill:productionSkill)
        cs.save()
        playerCharacter.addToCreatureSkills(cs)

        when:
        SkillRequirement.findByProduct(product)
        playerCharacter.creatureSkills.size() == 1
        productionSkill.initSkill(playerCharacter)
        
        then:
        PlayerProduct.findAllWhere([product: product, pc:playerCharacter]).size() == 1
        playerCharacter.playerProducts.contains(PlayerProduct.findByProduct(product))
    }

}
