package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class Mail {

    static belongsTo = [box:MailBox]

    static constraints = {
        content size:1..2000
        subject blank:true, size:1..80
    }

    PlayerCharacter sender
    PlayerCharacter recipient

    String subject
    String content
    Boolean shown = false
    Date sent = new Date()

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Mail)) return false

        Mail mail = o

        if (box != mail.box) return false
        if (content != mail.content) return false
        if (recipient != mail.recipient) return false
        if (sender != mail.sender) return false
        if (sent != mail.sent) return false
        if (shown != mail.shown) return false
        if (subject != mail.subject) return false

        return true
    }

    int hashCode() {
        int result        
        result = (subject != null ? subject.hashCode() : 0)
        result = 31 * result + (sent != null ? sent.hashCode() : 0)
        return result
    }
}
