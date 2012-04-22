package de.dewarim.goblin.pc

/**
 *
 */
class PlayerMessage {

    static belongsTo = [pc:PlayerCharacter]

    String pcMessage
    Boolean displayed = false
    Date datum = new Date()

}
