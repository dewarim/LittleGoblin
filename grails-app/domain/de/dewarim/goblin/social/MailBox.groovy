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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof MailBox)) return false

        MailBox mailBox = (MailBox) o

        if (boxType != mailBox.boxType) return false
        if (owner != mailBox.owner) return false

        return true
    }

    int hashCode() {
        int result
        result = (boxType != null ? boxType.hashCode() : 0)
        result = 31 * result + (owner != null ? owner.hashCode() : 0)
        return result
    }
}
