package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class ChatMessage {

    static belongsTo = [chatterBox:ChatterBox]

    static constraints = {
        content size:1..1024
    }

    PlayerCharacter sender
    String content
    Date sent = new Date()

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ChatMessage)) return false

        ChatMessage that = o

        if (chatterBox != that.chatterBox) return false
        if (content != that.content) return false
        if (sender != that.sender) return false
        if (sent != that.sent) return false

        return true
    }

    int hashCode() {
        return sent.hashCode()
    }
}
