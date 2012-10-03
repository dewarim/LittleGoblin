package de.dewarim.goblin.social

import de.dewarim.goblin.BaseController
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.pc.OrderApplication
import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class GoblinOrderController extends BaseController{

    def globalConfigService

    /**
     * Create a new order
     */
    @Secured(['ROLE_USER'])
    def show() {
        def pc = fetchPc()

        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            flash.message = message(code:'error.order.not_found')
            return redirect(controller:'town', action:'show', params:[pc:pc.id])
        }
        
        return [
                pc:pc,
                order:order
        ]
    }

    /**
     * create a new order
     */
    @Secured(['ROLE_USER'])
    def save() {
        def pc = fetchPc()

        if(pc.goblinOrder){
            flash.message = message(code:'error.order.already_a_member')
            return redirect(action:'index', controller:'goblinOrder', params:[pc:pc.id])
        }

        // pay for creation of a new order:
        Integer price = globalConfigService.fetchValueAsInt('coins.price.create_order')
        if(pc.user.coins < price){
            flash.message = message(code:'error.coins.missing')
            return redirect(action:'index', controller:'goblinOrder', params:[pc:pc.id])
        }

        GoblinOrder order = new GoblinOrder(name: params.order_name,
                description: params.order_description,
                leader: pc
        )
        if (! (order.validate() && order.save())) {

            return redirect(action: 'index',
                    controller: 'goblinOrder',
                    params: [pc: pc.id, order: order.id, saveFailed: true])
        }
        else{
            order.addToMembers(pc)
            pc.goblinOrder = order
            pc.user.coins = pc.user.coins - price
            return redirect(action:'showMyOrder', controller:'goblinOrder', params:[pc:pc.id, order:order.id])
        }
    }

    // public ajax function
    def checkOrderName() {
        log.debug("checkOrderName: ${params}")
        def name = params.order_name
        if(! name){
            return render(text:message(code:'order.select.name'))
        }
        else{
            GoblinOrder o = GoblinOrder.findByName(name)
            if(o){
                return render(status:503, text:message(code:'order.name.exists')) //"<span class='error'>${message(code:'order.name.exists')}</span>")
            }
            else{
                return render(text:message(code:'order.name.available'))
            }
        }
    }

    /**
     * List all orders
     */
    @Secured(['ROLE_USER'])
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

    @Secured(['ROLE_USER'])
    def describe() {
        def pc = fetchPc()
        if(! pc){
            return render(status:503, text:message(code:'error.player_not_found'))
        }
        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            return render(status:503, text:message(code:'error.order.not_found'))
        }
        return render(template:"/goblinOrder/order_description", model:[order:order])
    }

    /**
    * Show the main order screen
     */
    @Secured(['ROLE_USER'])
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
    @Secured(['ROLE_USER'])
    def apply() {
        def pc = fetchPc()

        GoblinOrder order = GoblinOrder.get(params.order)
        if(! order){
            flash.message = message(code:'error.order.not_found')
            return redirect(action:'show', controller:'town')
        }

        OrderApplication application = new OrderApplication(applicant:pc,
                applicationMessage:params.applicationMessage?.encodeAsHTML(),
                order:order
        )
        application.save()
        order.addToApplications(application)
        flash.message = message(code:'order.application.sent', args:[order.name])
        return redirect(action:'show', controller:'town')
    }

    @Secured(['ROLE_USER'])
    def showMyOrder() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            return redirect(controller:'town', action:'show')
        }
        def chatterBox = pc.goblinOrder.fetchDefaultChatterBox()
//        log.debug("chatterBox:${chatterBox}")
        return [
                pc:pc,
                order:pc.goblinOrder,
                currentBox:chatterBox
        ]
    }

    @Secured(['ROLE_USER'])
    def leave() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            return redirect(controller:'town', action:'show')
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
        return redirect(controller:'town', action:'show')
    }

    @Secured(['ROLE_USER'])
    def showMembers() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            return redirect(controller:'town', action:'show')
        }

        return [
                order:pc.goblinOrder,
                pc:pc
        ]

    }

    @Secured(['ROLE_USER'])
    def showApplications() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            flash.message = message(code:'error.order.orderless')
            return redirect(controller:'town', action:'show')
        }
        if( pc.goblinOrder.applications.isEmpty()){
            flash.message = message(code:'error.no.applicants')
            return redirect(controller:'goblinOrder', action:'showMyOrder')
        }

        return [
                order:pc.goblinOrder,
                pc:pc
        ]

    }

    @Secured(['ROLE_USER'])
    def acceptApplication() {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            return render(status:503, text: message(code:'error.order.orderless'))
        }
        if( pc.goblinOrder.applications.isEmpty()){
           return render(status:503, text: message(code:'error.no.applicants'))
        }

        OrderApplication app = OrderApplication.get(params.application)
        if(! app){
            return render(status:503, text:message(code:'error.application.not_found'))
        }
        // Note: the last order to accept an application wins...
        // TODO: If the player already was a member of an order, the old order should be notified...
        PlayerCharacter applicant = app.applicant
        applicant.goblinOrder = pc.goblinOrder
        pc.goblinOrder.addToMembers(app.applicant)
        // TODO: sent application.accepted message
        pc.goblinOrder.removeFromApplications app
        app.delete()
        return render(text:message(code:'order.application.accepted'))
    }

    @Secured(['ROLE_USER'])
    def denyApplication= {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            return render(status:503, text: message(code:'error.order.orderless'))
        }
        if( pc.goblinOrder.applications.isEmpty()){
           return render(status:503, text: message(code:'error.no.applicants'))
        }

        OrderApplication app = OrderApplication.get(params.application)
        if(! app){
            return render(status:503, text:message(code:'error.application.not_found'))
        }
        // TODO: sent application.denied message
        pc.goblinOrder.removeFromApplications app
        app.delete()
        return render(text:message(code:'order.application.denied'))
    }

    @Secured(['ROLE_USER'])
    def kickMember= {
        def pc = fetchPc()

        if(! pc.goblinOrder){
            return render(status:503, text: message(code:'error.order.orderless'))
        }

        PlayerCharacter member = PlayerCharacter.get(params.member)
        if(! member){
            return render(status:503, text:message(code:'error.member.not_found'))
        }

        if(member.goblinOrder?.equals(pc.goblinOrder)){
            member.goblinOrder = null
            pc.goblinOrder.removeFromMembers(member)
            // TODO: send banished.from.order message to player.
            return render(text:message(code:'order.member.kicked', args:[member.name]))
        }
        else{
            return render(status:503, text:message(code:'error.member.foreign'))
        }

    }
}
