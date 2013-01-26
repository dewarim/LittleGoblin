package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.combat.Melee
import de.dewarim.goblin.combat.MeleeFighter
import de.dewarim.goblin.combat.MeleeAction
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemTypeFeature

class MeleeController extends BaseController {

    def meleeService
    def itemService

    /**
     * Show list of current melee fighters and allow player to join in.
     * If now melee is running, the player may register for the next round.
     */
    @Secured(['ROLE_USER'])
    def index() {
        def pc = fetchPc()
        if (!pc) {
            return redirect(controller: 'portal', action: 'start')
        }
        return fetchViewParameters(pc)
    }

    /**
     * Join a melee round. There should always be one melee in status RUNNING or WAITING, which the
     * player may join.
     */
    @Secured(['ROLE_USER'])
    def join() {
        def pc = fetchPc()
        if (!pc) {
            flash.message = message(code: 'melee.join.failed')
            return redirect(controller: 'melee', action: 'index')
        }

        if (pc.currentMelee) {
            flash.message = message(code: 'melee.join.already')
            return redirect(controller: 'melee', action: index)
        }

        def melee = meleeService.findOrCreateMelee()
        log.debug("found melee: $melee")
        if (meleeService.newFighterAllowed(pc, melee)) {
            meleeService.joinMelee(pc, melee)
        }
        else {
            flash.message = message(code: 'melee.join.not.again')
            return redirect(controller: 'melee', action: index)
        }

        if (melee.status == MeleeStatus.WAITING) {
            return redirect(controller: 'melee', action: 'index')
        }
        else {
            return redirect(controller: 'melee', action: 'show',
                    params: [pc: pc, melee: melee, fighters: meleeService.listFighters(melee)])
        }
    }

    /**
     * Leave a melee round. A player may leave at any time.
     */
    @Secured(['ROLE_USER'])
    def leave() {
        def pc = fetchPc()
        if (!pc || pc.currentMelee == null) {
            flash.message = message(code: 'melee.leave.failed')
        }
        else {
            meleeService.leaveMelee(pc, pc.currentMelee)
            flash.message = message(code: 'melee.left.melee')
        }
        return redirect(controller: 'melee', action: 'index')
    }

    @Secured(['ROLE_USER'])
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
            def ids = itemFeature.split('__');
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
            return render(template: 'chooseAction', model: fetchViewParameters(pc))
        }
        catch (Exception e) {
            if (pc) {
                def p = fetchViewParameters(pc)
                p.put('meleeMessage', message(code: e.getMessage()))
                return render(template: 'chooseAction', model: p)
            }
            else {
                return render(status: 503, text: message(code: e.getMessage()))
            }
        }
    }

    @Secured(['ROLE_USER'])
    def updateActions() {
        def pc = fetchPc()
        if (!pc) {
            return render(status: 503, text: message(code: 'melee.error'))
        }
        return render(template: 'chooseAction', model: fetchViewParameters(pc))
    }

    @Secured(['ROLE_USER'])
    def updateFighterList() {
        def pc = fetchPc()
        if (!pc) {
            return render(status: 503, text: message(code: 'melee.error'))
        }
        Melee melee = meleeService.findOrCreateMelee()
        return render(template: 'fighters', model: [pc: pc, fighters: meleeService.listFighters(melee)])
    }

    @Secured(['ROLE_USER'])
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
            return render(template: 'chooseAction', model: p)


        }
        catch (Exception e) {
            return render(status: 503, text: message(code: e.getMessage()))
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
