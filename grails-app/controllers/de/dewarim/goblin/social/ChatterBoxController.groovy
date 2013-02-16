package de.dewarim.goblin.social

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController

@Secured(['ROLE_USER'])
class ChatterBoxController extends BaseController{

    def globalConfigService

    def showChatterBox() {
        def pc = fetchPc()
        ChatterBox box = ChatterBox.get(params.box)
        if(! box){
            render(status:503, text:message(code:'error.chatterbox.not.found'))
            return
        }
        if(box.goblinOrder.equals(pc.goblinOrder)){
            render(template:"/chatterBox/box", model:[currentBox:box, pc:pc])
        }
        else{
            render(status:503, text:message(code:'error.chatterbox.foreign'))
        }
    }

    def sendChatMessage() {
//        log.debug("session: $session")
        def pc = fetchPc()
        ChatterBox box = ChatterBox.get(params.box)
        if(! box){
            render(status:503, text:message(code:'error.chatterbox.not.found'))
            return
        }
        def msg = params.chatMessage?.trim()
        if( msg == null || msg.length() == 0){
            render(status:503, text:message(code:'error.chatterbox.empty.message'))
            return
        }
//        log.debug("${box?.goblinOrder}")
//        log.debug("${pc?.goblinOrder}")
        if(box.goblinOrder.equals(pc.goblinOrder)){
            ChatMessage cm = new ChatMessage(sender:pc,
                    content:msg,
                    chatterBox:box
            )
            box.addToChatMessages(cm)
            cm.save()
            def maxMessages = globalConfigService.fetchValueAsInt('order.chatterbox.max.messages') ?: 10
            if(box.chatMessages.size() > maxMessages ){
                box.rightSize(maxMessages)
            }
            render(template:"/chatterBox/box", model:[currentBox:box, pc:pc])
        }
        else{
            render(status:503, text:message(code:'error.chatterbox.foreign'))
        }
    }
}
