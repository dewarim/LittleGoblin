package de.dewarim.goblin

import grails.plugins.springsecurity.Secured

import de.dewarim.goblin.item.Item
import de.dewarim.goblin.pc.PlayerCharacter

class PostOfficeController extends BaseController {

    def inputValidationService
    def postOfficeService

    /**
     * Main overview of post office
     */
    @Secured(['ROLE_USER'])
    def index = {
        def pc = fetchPc()
        if(! pc){
            return redirect(controller: 'portal', action: 'start')
        }
        def itemList = pc.items.sort {message(code:it.type.name)}
        return [pc: pc,
                items: itemList
        ]
    }

    @Secured(['ROLE_USER'])
    def sendItem = {
        def pc = fetchPc()
        try {
            // 1. find the recipient.
            PlayerCharacter recipient = PlayerCharacter.find("from PlayerCharacter as pc where lower(pc.name) = lower(:name)",[name:params.recipient])
            if(recipient == null){
                throw new RuntimeException('error.recipient.not.found')
            }

            // 2. find the item.
            Item item = (Item) inputValidationService.checkObject(Item.class, params.item)
            if (!item) {
                throw new RuntimeException('error.object.not.found')
            }
            if( ! item.owner.equals(pc) ){
                throw new RuntimeException('error.wrong_owner')
            }

            // 3. determine amount
            def amount = 1
            if(item.type.stackable){
                amount = inputValidationService.checkAndEncodeInteger(params, 'amount', 'item.amount')
            }

            // 4. try to pay for sending
            Integer transferCost = postOfficeService.computeTransferCost(amount, item)
            if(pc.gold < transferCost ){
                throw new RuntimeException('error.insufficient.funds')
            }
            log.debug("send item: $amount ${item.type.name} to ${recipient.name} for $transferCost")
            postOfficeService.sendItem(amount, item, pc, recipient)

            // 5. update itemList and add success-message.
            render(template: 'itemList', model: [items:pc.items, sendSuccess:true])
        }
        catch (RuntimeException e) {
            renderException e
        }
    }

    @Secured(['ROLE_USER'])
    def loadInventory = {
        def pc = fetchPc()
        if(! pc){
            render(status: 503, text:message(code:'error.player_not_found'))
        }
        render(template: '/shared/sideInventory', model:[pc:pc])
    }
}
