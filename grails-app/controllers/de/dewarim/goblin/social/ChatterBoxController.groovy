package de.dewarim.goblin.social

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController

class ChatterBoxController extends BaseController{

    def session
    def globalConfigService

    @Secured(['ROLE_USER'])
    def showChatterBox = {
        def pc = fetchPc(session)
        ChatterBox box = ChatterBox.get(params.box)
        if(! box){
            return render(status:503, text:message(code:'error.chatterbox.not.found'))
        }
        if(box.goblinOrder.equals(pc.goblinOrder)){
            return render(template:"/chatterBox/box", model:[currentBox:box, pc:pc])
        }
        else{
            return render(status:503, text:message(code:'error.chatterbox.foreign'))
        }
    }

    @Secured(['ROLE_USER'])
    def sendChatMessage = {
//        log.debug("session: $session")
        def pc = fetchPc(session)
        ChatterBox box = ChatterBox.get(params.box)
        if(! box){
            return render(status:503, text:message(code:'error.chatterbox.not.found'))
        }
        def msg = params.chatMessage?.trim()
        if( msg == null || msg.length() == 0){
            return render(status:503, text:message(code:'error.chatterbox.empty.message'))
        }
//        log.debug("${box?.goblinOrder}")
//        log.debug("${pc?.goblinOrder}")
        if(box.goblinOrder.equals(pc.goblinOrder)){
            ChatMessage cm = new ChatMessage(sender:pc,
                    content:msg.encodeAsHTML(),
                    chatterBox:box
            )
            box.addToChatMessages(cm)
            cm.save()
            def maxMessages = globalConfigService.fetchValueAsInt('order.chatterbox.max.messages') ?: 10
            if(box.chatMessages.size() > maxMessages ){
                box.rightSize(maxMessages)
            }
            return render(template:"/chatterBox/box", model:[currentBox:box, pc:pc])
        }
        else{
            return render(status:503, text:message(code:'error.chatterbox.foreign'))
        }
    }
}
