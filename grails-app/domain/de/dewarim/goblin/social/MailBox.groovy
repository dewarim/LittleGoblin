package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class MailBox {

    static belongsTo = [owner:PlayerCharacter, boxType:MailBoxType]
    static hasMany = [mails:Mail]

    MailBox(){}

    MailBox(PlayerCharacter pc, MailBoxType type){
        owner = pc
        boxType = type
        pc.addToMailBoxes this
        boxType.addToBoxes this
    }

    Integer countUnreadMail(){
        return mails.findAll{ ! it.shown}.size()
    }

    Boolean typeIsArchive(){
        return boxType.name.equals('mail.archive')
    }
    
}
