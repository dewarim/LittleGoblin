package de.dewarim

import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.skill.ProductionSkill
import org.springframework.validation.FieldError
import spock.lang.Specification

/**
 * see: http://www.christianoestreich.com/2012/11/domain-constraints-grails-spock-updated/
 */
class ConstraintUnitSpec extends Specification{

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors.hasFieldErrors(field)
            obj.errors.getFieldErrors(field).each{FieldError fieldError ->
                fieldError.codes.contains(error)
            }
        } else {
            assert !obj.errors[field]
        }
    }
    
    UserAccount getUserAccount(){
        return new UserAccount(username: "Test-o-User")
    }
    
    PlayerCharacter getPlayerCharacter(){
        return new PlayerCharacter(name: "Felidae Canem", user: userAccount)
    }
    
    ItemType getItemType(){
        return new ItemType(name:'Crown of Fabulousness')
    }
    
    Item getItem(PlayerCharacter pc){
        return new Item(itemType, pc)
    }
    
    ProductionSkill getProductionSkill(){
        return new ProductionSkill(name: 'acme.maker.skill')
    }
    
    ProductCategory getProductCategory(){
        return new ProductCategory(name: "everything")
    }
    
    Product getProduct(){
        return new Product(name: "acme",
                category: productCategory,
                timeNeeded: 1
        )
    }

    /**
     * @return default skill skill requirement for the default 
     * product and productionSkill.
     */
    SkillRequirement getDefaultSkillRequirement(){
        new SkillRequirement(skill:productionSkill, product: product)
    }
    
    
}
