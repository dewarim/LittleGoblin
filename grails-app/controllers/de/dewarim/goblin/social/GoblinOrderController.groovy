package de.dewarim.goblin.social

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.pc.OrderApplication
import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
@Secured(['ROLE_USER'])
class GoblinOrderController extends BaseController{

    def globalConfigService

    /**
     * Create a new order
     */
    def show() {
        def pc = fetchPc()

        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            flash.message = message(code:'error.order.not_found')
            redirect(controller:'town', action:'show', params:[pc:pc.id])
            return
        }

        return [
                pc:pc,
                order:order
        ]
    }

    /**
     * create a new order
     */
    def save() {
        def pc = fetchPc()

        if(pc.goblinOrder){
            flash.message = message(code:'error.order.already_a_member')
            redirect(action:'index', controller:'goblinOrder', params:[pc:pc.id])
            return
        }

        // pay for creation of a new order:
        Integer price = globalConfigService.fetchValueAsInt('coins.price.create_order')
        if(pc.user.coins < price){
            flash.message = message(code:'error.coins.missing')
            redirect(action:'index', controller:'goblinOrder', params:[pc:pc.id])
            return
        }

        GoblinOrder order = new GoblinOrder(name: params.order_name,
                description: params.order_description,
                leader: pc
        )
        
        if (! (order.validate() && order.save())) {
            ChatterBox chatterBox = new ChatterBox(name: 'chatterbox.default.name', goblinOrder: order)            
            order.addToChatterBoxes(chatterBox)
            chatterBox.save()
            
            redirect(action: 'index',
                    controller: 'goblinOrder',
                    params: [pc: pc.id, order: order.id, saveFailed: true])
        }
        else{
            order.addToMembers(pc)
            pc.goblinOrder = order
            pc.user.coins = pc.user.coins - price
            redirect(action:'showMyOrder', controller:'goblinOrder', params:[pc:pc.id, order:order.id])
        }
    }

    // public ajax function
    @Secured(['permitAll'])
    def checkOrderName() {
        log.debug("checkOrderName: ${params}")
        def name = params.order_name
        if(! name){
            render(text:message(code:'order.select.name'))
        }
        else{
            GoblinOrder o = GoblinOrder.findByName(name)
            if(o){
                render(status:503, text:message(code:'order.name.exists')) //"<span class='error'>${message(code:'order.name.exists')}</span>")
            }
            else{
                render(text:message(code:'order.name.available'))
            }
        }
    }

    /**
     * List all orders
     */
    def list() {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        render(template:'/goblinOrder/order_list',
                model:[
                pc:pc,
                max:max,
                offset:offset,
                orders:GoblinOrder.listOrderByName(max:max, offset:offset),
                ]
                )
    }

    def describe() {
        def pc = fetchPc()
        if(! pc){
            render(status:503, text:message(code:'error.player_not_found'))
            return
        }
        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            render(status:503, text:message(code:'error.order.not_found'))
            return
        }
        render(template:"/goblinOrder/order_description", model:[order:order])
    }

    /**
    * Show the main order screen
     */
    def index() {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        return [
                pc:pc,
                max:max,
                offset:offset,
                globalConfigService:globalConfigService,
                saveFailed:params.saveFailed,
                orders:GoblinOrder.listOrderByName(max:max, offset:offset)
        ]
    }

    /**
     * Join an existing order
     */
    def apply() {
        def pc = fetchPc()

        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            flash.message = message(code:'error.order.not_found')
            redirect(action:'show', controller:'town')
            return
        }

        OrderApplication application = new OrderApplication(applicant:pc,
                applicationMessage:params.applicationMessage?.encodeAsHTML(),
                order:order
        )
        application.save()
        order.addToApplications(application)
        flash.message = message(code:'order.application.sent', args:[order.name])
        redirect(action:'show', controller:'town')
    }

    def showMyOrder() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            redirect(controller:'town', action:'show')
            return
        }
        def chatterBox = pc.goblinOrder.fetchDefaultChatterBox()
//        log.debug("chatterBox:${chatterBox}")
        return [
                pc:pc,
                order:pc.goblinOrder,
                currentBox:chatterBox
        ]
    }

    def leave() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            redirect(controller:'town', action:'show')
            return
        }

        GoblinOrder order = pc.goblinOrder
        pc.goblinOrder = null
        order.removeFromMembers(pc)

        if(order.members?.isEmpty()){
            order.leader = null // otherwise, a FK-exception is thrown.
            log.info("${order.name} is being disbanded.")
            order.delete()
        }
        else if(order.leader?.equals(pc)){
            order.leader = order.members.find{true} // pick the first available member.
        }

        flash.message = message(code:'order.left', args:[order.name])
        redirect(controller:'town', action:'show')
    }

    def showMembers() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            redirect(controller:'town', action:'show')
            return
        }

        return [
                order:pc.goblinOrder,
                pc:pc
        ]

    }

    def showApplications() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            redirect(controller:'town', action:'show')
            return
        }
        if( pc.goblinOrder.applications.isEmpty()){
            flash.message = message(code:'error.no.applicants')
            redirect(controller:'goblinOrder', action:'showMyOrder')
            return
        }

        return [
                order:pc.goblinOrder,
                pc:pc
        ]

    }

    def acceptApplication() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            render(status:503, text: message(code:'error.order.orderless'))
            return
        }
        if( pc.goblinOrder.applications.isEmpty()){
           render(status:503, text: message(code:'error.no.applicants'))
           return
        }

        OrderApplication app = OrderApplication.get(params.application)
        if(! app){
            render(status:503, text:message(code:'error.application.not_found'))
            return
        }
        // Note: the last order to accept an application wins...
        // TODO: If the player already was a member of an order, the old order should be notified...
        PlayerCharacter applicant = app.applicant
        applicant.goblinOrder = pc.goblinOrder
        pc.goblinOrder.addToMembers(app.applicant)
        // TODO: sent application.accepted message
        pc.goblinOrder.removeFromApplications app
        app.delete()
        render(text:message(code:'order.application.accepted'))
    }

    def denyApplication() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            render(status:503, text: message(code:'error.order.orderless'))
            return
        }
        if( pc.goblinOrder.applications.isEmpty()){
           render(status:503, text: message(code:'error.no.applicants'))
           return
        }

        OrderApplication app = OrderApplication.get(params.application)
        if(! app){
            render(status:503, text:message(code:'error.application.not_found'))
            return
        }
        // TODO: sent application.denied message
        pc.goblinOrder.removeFromApplications app
        app.delete()
        render(text:message(code:'order.application.denied'))
    }

    def kickMember() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            render(status:503, text: message(code:'error.order.orderless'))
            return
        }

        PlayerCharacter member = PlayerCharacter.get(params.member)
        if(! member){
            render(status:503, text:message(code:'error.member.not_found'))
            return
        }

        if(member.goblinOrder?.equals(pc.goblinOrder)){
            member.goblinOrder = null
            pc.goblinOrder.removeFromMembers(member)
            // TODO: send banished.from.order message to player.
            render(text:message(code:'order.member.kicked', args:[member.name]))
        }
        else{
            render(status:503, text:message(code:'error.member.foreign'))
        }
    }
}
