package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class ChatMessage {

    static belongsTo = [chatterBox:ChatterBox]

    static constraints = {
        sender nullable:false
        content size:1..1024
    }

    PlayerCharacter sender
    String content
    Date sent = new Date()

}
