package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.crafting.Product
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.skill.Skill

/**
 * Manage the SkillRequirements of a specific product.
 */
@Secured(["ROLE_ADMIN"])
class SkillRequirementAdminController extends BaseController {

    def index() {
        try {
            def product = inputValidationService.checkObject(Product.class, params.id)
            return [
                    product: product, requirement: null
            ]
        }
        catch (Exception e) {
            flash.message = message(code: 'error.invalid.object')
            redirect(controller: 'productAdmin', action: 'index')
        }
    }

    def edit() {
        try {
            def requirement = inputValidationService.checkObject(SkillRequirement.class, params.id)
            render(template: 'edit', model: [product: requirement.product, requirement: requirement])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def cancelEdit() {
        try {
            def requirement = inputValidationService.checkObject(SkillRequirement.class, params.id)
            render(template: 'update', model: [product: requirement.product, requirement: requirement])
        }
        catch (Exception e) {
            renderException e
        }
    }

    def update() {
        try {
            def requirement = inputValidationService.checkObject(SkillRequirement.class, params.id)
            updateFields(requirement)
            requirement.save()
            render(template: 'update', model: [product: requirement.product, requirement: requirement])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    /**
     * Add a SkillRequirement to a product. #AJAX
     */
    def save() {
        try {
            Product product = (Product) inputValidationService.checkObject(Product.class, params.product)
            SkillRequirement requirement = new SkillRequirement(product: product)
            updateFields(requirement)
            def exists = SkillRequirement.find("from SkillRequirement s where s.product=:product and s.skill=:skill",
            [product:requirement.product, skill:requirement.skill])
            if(exists){
                throw new RuntimeException(message(code:"error.requirement.exists"))
            }

            product.addToRequiredSkills(requirement)
            requirement.save()
            render(template: 'list', model: [product: product, requirement: requirement])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    /**
     * Delete a message from a reputationMessageMap. #AJAX
     */
    def delete() {
        try {
            SkillRequirement requirement = (SkillRequirement) inputValidationService.checkObject(SkillRequirement, params.id)
            Product product = requirement.product
            requirement.deleteFully()
            product.refresh()
            render(template: 'list', model: [product: product, requirement: requirement])
        }
        catch (RuntimeException e) {
            log.debug("failed to delete; ", e)
            renderException e
        }
    }

    protected void updateFields(SkillRequirement requirement) {
        requirement.level = inputValidationService.checkAndEncodeInteger(params, "level", "requirement.level")
        requirement.skill = inputValidationService.checkObject(Skill.class, params.skill)
    }

}
