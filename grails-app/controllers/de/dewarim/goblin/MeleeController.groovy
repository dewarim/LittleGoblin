package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.combat.Melee
import de.dewarim.goblin.combat.MeleeFighter
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemTypeFeature
import de.dewarim.goblin.pc.PlayerCharacter

@Secured(['ROLE_USER'])
class MeleeController extends BaseController {

    def meleeService
    def itemService

    /**
     * Show list of current melee fighters and allow player to join in.
     * If now melee is running, the player may register for the next round.
     */
    def index() {
        def pc = fetchPc()
        if (!pc) {
            redirect(controller: 'portal', action: 'start')
            return
        }
        return fetchViewParameters(pc)
    }

    /**
     * Join a melee round. There should always be one melee in status RUNNING or WAITING, which the
     * player may join.
     */
    def join() {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code: 'melee.join.failed')
            redirect(controller: 'melee', action: 'index')
            return
        }

        if (pc.currentMelee) {
            flash.message = message(code: 'melee.join.already')
            redirect(controller: 'melee', action: 'index')
            return
        }

        def melee = meleeService.findOrCreateMelee()
        log.debug("found melee: $melee")
        if (meleeService.newFighterAllowed(pc, melee)) {
            meleeService.joinMelee(pc, melee)
        }
        else {
            flash.message = message(code: 'melee.join.not.again')
            redirect(controller: 'melee', action: 'index')
            return
        }

        if (melee.status == MeleeStatus.WAITING) {
            redirect(controller: 'melee', action: 'index')
        }
        else {
            redirect(controller: 'melee', action: 'show',
                    params: [pc: pc, melee: melee, fighters: meleeService.listFighters(melee)])
        }
    }

    /**
     * Leave a melee round. A player may leave at any time.
     */
    def leave() {
        def pc = fetchPc()
        if (!pc || pc.currentMelee == null) {
            flash.message = message(code: 'melee.leave.failed')
        }
        else {
            meleeService.leaveMelee(pc, pc.currentMelee)
            flash.message = message(code: 'melee.left.melee')
        }
        redirect(controller: 'melee', action: 'index')
    }

    def useItem() {
        def pc = fetchPc()
        try {
            if (!pc) {
                throw new RuntimeException('melee.error')
            }
            def p = fetchViewParameters(pc)
            PlayerCharacter adversary = (PlayerCharacter) inputValidationService.checkObject(PlayerCharacter.class, params.adversary)
            String itemFeature = params.itemFeature
            if (!itemFeature) {
                throw new RuntimeException('melee.action.fail')
            }
            def ids = itemFeature.split('__')
            if (ids.size() != 2) {
                throw new RuntimeException('melee.action.fail')
            }
            Item item = (Item) inputValidationService.checkObject(Item.class, ids[0])
            ItemTypeFeature itemTypeFeature = (ItemTypeFeature) inputValidationService.checkObject(ItemTypeFeature.class, ids[1])
            if (!item || !itemTypeFeature) {
                throw new RuntimeException('melee.action.fail')
            }
            if (item?.type != itemTypeFeature?.itemType) {
                throw new RuntimeException('melee.action.fail')
            }
            // The player needs to own the item and has to carry it:
            if (item.owner != pc || item.location != ItemLocation.ON_PERSON) {
                throw new RuntimeException('melee.action.fail')
            }

            // ok, useItem request seems valid:
            meleeService.addUseItemAction(pc, adversary, item, itemTypeFeature)
            render(template: 'chooseAction', model: fetchViewParameters(pc))
        }
        catch (Exception e) {
            if (pc) {
                def p = fetchViewParameters(pc)
                p.put('meleeMessage', message(code: e.getMessage()))
                render(template: 'chooseAction', model: p)
            }
            else {
                render(status: 503, text: message(code: e.getMessage()))
            }
        }
    }

    def updateActions() {
        def pc = fetchPc()
        if (!pc) {
            render(status: 503, text: message(code: 'melee.error'))
            return
        }
        render(template: 'chooseAction', model: fetchViewParameters(pc))
    }

    def updateFighterList() {
        def pc = fetchPc()
        if (!pc) {
            render(status: 503, text: message(code: 'melee.error'))
            return
        }
        Melee melee = meleeService.findOrCreateMelee()
        render(template: 'fighters', model: [pc: pc, fighters: meleeService.listFighters(melee)])
    }

    def attack() {
        try {
            def pc = fetchPc()
            if (!pc) {
                throw new RuntimeException('melee.error')
            }
            def p
            PlayerCharacter adversary = inputValidationService.checkObject(PlayerCharacter.class, params.adversary)
            if (meleeService.checkAdversary(pc, adversary)) {
                if (meleeService.fetchAction(pc)) {
                    // player has already selected an action
                    p = fetchViewParameters(pc)
                    p.put('meleeMessage', 'melee.action.selected')
                }
                else {
                    meleeService.addAttackAction(pc, adversary)
                    p = fetchViewParameters(pc)
                }
            }
            else {
                /*
                 * Adversary is invalid - set message, but do nothing
                 * This can happen if a PC flees from the melee or dies while the player
                 * is still making his decision.
                 */
                p = fetchViewParameters(pc)
                p.put('meleeMessage', 'melee.adversary.missing')
            }
            render(template: 'chooseAction', model: p)
        }
        catch (Exception e) {
            render(status: 503, text: message(code: e.getMessage()))
        }
    }

    protected Map fetchViewParameters(PlayerCharacter pc) {
        def items = []
        def fighters
        def opponents = []
        def waitingMelee = null
        def runningMelee = Melee.findByStatus(MeleeStatus.RUNNING)
        def currentAction = null
        if (runningMelee) {
            fighters = meleeService.listFighters(runningMelee)
            if (pc.currentMelee) {
                items = itemService.fetchUsableItems(pc)
                MeleeFighter mf = MeleeFighter.find("from MeleeFighter mf where mf.melee=:melee and mf.pc=:pc ",
                    [melee: pc.currentMelee, pc: pc])
                currentAction = mf.action
                opponents = fighters.findAll{it != pc}
            }
        }
        else {
            waitingMelee = Melee.findByStatus(MeleeStatus.WAITING)
            if (!waitingMelee) {
                waitingMelee = meleeService.createMelee()
            }
            fighters = meleeService.listFighters(waitingMelee)
        }
        return [pc: pc, runningMelee: runningMelee,
                waitingMelee: waitingMelee,
                fighters: fighters,
                opponents: opponents, // just fighters minus pc ... should refactor somehow.
                items: items,
                currentAction: currentAction]
    }

}
