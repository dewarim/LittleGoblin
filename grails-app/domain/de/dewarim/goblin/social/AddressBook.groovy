package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class AddressBook {

    static hasMany = [recipients:PlayerCharacter]
    static belongsTo = [owner:PlayerCharacter]
}
