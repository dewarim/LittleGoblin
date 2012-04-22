package de.dewarim.goblin.social

import de.dewarim.goblin.pc.GoblinOrder

class ChatterBox {

    static hasMany = [chatMessages:ChatMessage]
    static belongsTo = [goblinOrder:GoblinOrder]

    static constraints = {
        name nullable:false
    }

    Long messages = 0
    String name

    void rightSize(newSize){
        def messages = chatMessages.sort{ it.sent.time }.reverse()
        if(newSize >= messages.size()){
            return // nothing to do.
        }
        else{
            def toDelete = messages.getAt(newSize .. messages.size()-1)
            toDelete.each{cmsg ->
                removeFromChatMessages(cmsg)
                cmsg.delete()
            }
        }
    }
}
