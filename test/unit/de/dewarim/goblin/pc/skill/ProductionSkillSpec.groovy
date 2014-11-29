package de.dewarim.goblin.pc.skill

import de.dewarim.ConstraintUnitSpec
import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.pc.crafting.PlayerProduct
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Test specification for class ProductionSkill.
 * Note: basic tests for its abstract parent class "Skill" can be found in CombatSkillSpec.
 */
@TestFor(ProductionSkill)
@Mock([SkillRequirement, UserAccount, CreatureSkill, PlayerProduct, PlayerCharacter, Product, ProductCategory, Skill])
class ProductionSkillSpec extends Specification {

    def user = new UserAccount(username: "crafty", passwd: 'foodFood', userRealName: 'none')
    def playerCharacter = new PlayerCharacter(name: "Crafter-1", user: user)
    def prodCat = new ProductCategory(name: "head.wear")
    def crownProduct = new Product(name: "Crown Product", timeNeeded: 1, category: prodCat)
    def skill = new ProductionSkill(name: 'crown prod skill')
    def skillRequirement = new SkillRequirement(skill: skill, product: crownProduct)
    def creatureSkill = new CreatureSkill(owner: playerCharacter, skill: skill)
    
    void setup() {
        user.save()
        playerCharacter.save()
        prodCat.save()
        crownProduct.save()
        skill.save()
        skillRequirement.save()
        creatureSkill.save()
        playerCharacter.addToCreatureSkills(creatureSkill)
    }

    void "initSkillTest"() {
        when:
        skill.initSkill(playerCharacter)

        then:
        PlayerProduct.findAllWhere([product: crownProduct, pc: playerCharacter]).size() == 1
        playerCharacter.playerProducts.contains(PlayerProduct.findByProduct(crownProduct))
    }

}
