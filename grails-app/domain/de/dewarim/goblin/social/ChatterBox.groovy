package de.dewarim.goblin.social

import de.dewarim.goblin.pc.GoblinOrder

class ChatterBox {

    static hasMany = [chatMessages:ChatMessage]
    static belongsTo = [goblinOrder:GoblinOrder]

    static constraints = {
    }

    Long messages = 0
    String name

    void rightSize(newSize){
        def messages = chatMessages.sort{ it.sent.time }.reverse()
        if(newSize < messages.size()){
            def toDelete = messages.getAt(newSize .. messages.size()-1)
            toDelete.each{cmsg ->
                removeFromChatMessages(cmsg)
                cmsg.delete()
            }
        }
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ChatterBox)) return false

        ChatterBox that = o

        if (goblinOrder != that.goblinOrder) return false
        if (messages != that.messages) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (messages != null ? messages.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (goblinOrder != null ? goblinOrder.hashCode() : 0)
        return result
    }
}
