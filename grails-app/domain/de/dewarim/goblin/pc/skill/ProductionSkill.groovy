package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.PlayerProduct
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.SkillRequirement

/**
 *
 */
class ProductionSkill extends Skill{

    /*
     * Currently, ProductionSkill has no own fields. This class is simply used to differentiate
     * between CombatSkills and ProductionSkills.
     */


    /**
     * Initialize this skill. This is used to compute any new products that the player may now create.
     * @param pc
     */
    void initSkill(PlayerCharacter pc){
        // find all products which are not yet in this player's list of products.
        Collection<Product> products = skillRequirements.findAll{ requirement ->
            ! pc.playerProducts.find{ it.product.equals(requirement.product) }
        }.collect{it.product}
        log.debug("Player can create ${products.size()} new products.")
        // if the player now has all required skills, add this product to his portfolio
        products.each{ product ->
            SkillRequirement unknownSkill = product.requiredSkills.find{ requirement ->
                // find a skill which the player does not yet know:
                ! pc.creatureSkills.find{it.skill.equals(requirement.skill)}
            }

            if(unknownSkill){
                log.debug("Player still lacks the required skill ${unknownSkill.skill.name} for ${product.name}")
            }
            else{
                def playerProduct = new PlayerProduct(pc:pc, product:product)
                pc.addToPlayerProducts(playerProduct)
                product.addToCrafters(playerProduct)
                playerProduct.save()
                log.debug("Added playerProduct $playerProduct")
            }
        }

    }
}
