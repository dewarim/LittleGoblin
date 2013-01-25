package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.Dice
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Creature
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.skill.CombatSkill
import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.shop.ShopOwner

/**
 * Dice administration.
 */
// TODO: paging.
// TODO: search by name field.
@Secured(["ROLE_ADMIN"])
class DiceAdminController extends BaseController {

    def index() {
        return [
                dices: Dice.list(sort:params.sort ?: 'name', order: params.order ?: 'asc')
        ]
    }

    def edit() {
        def dice = Dice.get(params.id)
        if (!dice) {
            return render(status: 503, text: message(code: 'error.unknown.dice'))
        }
        render(template: '/diceAdmin/edit', model: [dice: dice])
    }

    def cancelEdit() {
        def dice = Dice.get(params.id)
        if (!dice) {
            return render(status: 503, text: message(code: 'error.unknown.dice'))
        }
        render(template: '/diceAdmin/update', model: [dice: dice])
    }

    def update() {
        try {
            def dice = Dice.get(params.id)
            if (!dice) {
                throw new RuntimeException('error.unknown.dice')
            }
            updateFields dice
            dice.save()
            render(template: '/diceAdmin/update', model: [dice: dice])
        }
        catch (RuntimeException e) {
//            log.debug("failed to update dice:",e)
            renderException e
        }
    }

    void updateFields(dice){
        dice.name = inputValidationService.checkAndEncodeName(params.name ?: dice.name, dice)
        dice.sides =
            inputValidationService.checkAndEncodeInteger(params, "sides", "dice.sides")
        dice.amount =
            inputValidationService.checkAndEncodeInteger(params, "amount", "dice.amount")
        dice.bonus =
            inputValidationService.checkAndEncodeInteger(params, 'bonus', 'dice.bonus')
    }

    def save() {
        Dice dice = new Dice()
        try {
           updateFields(dice)
           dice.save()

           render(template: '/diceAdmin/list', model: [dices: Dice.listOrderByName()])
        }
        catch (RuntimeException e) {
            renderException(e)
        }
    }

    def delete() {
        Dice dice = Dice.get(params.id)
        try {
            if (!dice) {
                throw new RuntimeException("error.object.not.found")
            }
            // check if dice is used in one of the many tables:
            if(Creature.findByStrike(dice) ||
                    Creature.findByParry(dice) ||
                    Creature.findByDamage(dice) ||
                    Creature.findByInitiative(dice)
            ){
                throw new RuntimeException("error.dice.used.by.creature")
            }
            if(CombatSkill.findByStrike(dice) ||
                    CombatSkill.findByParry(dice) ||
                    CombatSkill.findByDamage(dice) ||
                    CombatSkill.findByInitiative(dice)
            ){
                throw new RuntimeException("error.dice.used.by.creature")
            }

            if(ItemType.findByCombatDice(dice)){
                throw new RuntimeException("error.dice.used.by.itemType")
            }
            if(ShopOwner.findByPriceModifierDice(dice)){
                throw new RuntimeException("error.dice.used.by.shopOwner")
            }
            dice.delete()
            render(text: message(code: 'dice.deleted'))
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

}
