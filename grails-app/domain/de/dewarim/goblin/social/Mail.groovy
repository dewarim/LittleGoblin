package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class Mail {

    static belongsTo = [box:MailBox]

    static constraints = {
        sender nullable:false
        recipient nullable:false
        content size:1..2000
        subject blank:true, size:1..80
    }

    PlayerCharacter sender
    PlayerCharacter recipient

    String subject
    String content
    Boolean shown = false
    Date sent = new Date()
}
