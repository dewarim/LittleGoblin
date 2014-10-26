package de.dewarim

import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.skill.ProductionSkill
import de.dewarim.goblin.pc.skill.Skill
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
            def hasRequiredError = false
            obj.errors.getFieldErrors(field).each{FieldError fieldError ->
                if(fieldError.codes.contains(error)){
                    hasRequiredError = true
                }
            }
            if(! hasRequiredError){
                assert obj.errors == "Could not find error type '$error' on field '$field'"
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
        return getItemType('Crown of Fabulousness')
    }
    
    ItemType getItemType(name){
        return new ItemType(name:name)
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
        def product = new Product(name: "acme",
                category: productCategory,
                timeNeeded: 1
        )
        return product
    }
    
    Component getComponent(type, product){
        return new Component(type:type, product:product)
    }

    /**
     * @return default skill skill requirement for the default 
     * product and productionSkill.
     */
    SkillRequirement getDefaultSkillRequirement(){
        new SkillRequirement(skill:productionSkill, product: product)
    }
    
    SkillRequirement getSkillRequirement(Skill skill, Product product){
        def requirement = new SkillRequirement(skill:skill, product:product)
        skill.addToSkillRequirements(requirement)
        product.addToRequiredSkills(requirement)
        return requirement
    }
    
}
