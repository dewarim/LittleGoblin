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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ChatMessage)) return false

        ChatMessage that = (ChatMessage) o

        if (chatterBox != that.chatterBox) return false
        if (content != that.content) return false
        if (sender != that.sender) return false
        if (sent != that.sent) return false

        return true
    }

    int hashCode() {
        int result
        result = (sender != null ? sender.hashCode() : 0)
        result = 31 * result + (content != null ? content.hashCode() : 0)
        result = 31 * result + (sent != null ? sent.hashCode() : 0)
        result = 31 * result + (chatterBox != null ? chatterBox.hashCode() : 0)
        return result
    }
}
