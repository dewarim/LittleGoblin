package de.dewarim.goblin.social

import de.dewarim.goblin.pc.PlayerCharacter

/**
 *
 */
class AddressBook {

    static hasMany = [recipients:PlayerCharacter]
    static belongsTo = [owner:PlayerCharacter]

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof AddressBook)) return false

        AddressBook that = o

        if (owner != that.owner) return false

        return true
    }

    int hashCode() {
        return (owner != null ? owner.hashCode() : 0)
    }
}
