package de.dewarim.goblin.town

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.shop.Shop

/**
 * Implements a town (or comparable location) which the player may visit.
 * Currently, travel is not implemented yet.
 */
class Town {

    static hasMany = [shops    : Shop,
                      academies: Academy,
    ]
    static constraints = {
        name(blank: false)
        shortDescription(blank: false)
        description(nullable: true, blank: false, size: 1..4000)
    }

    String name
    String description
    String shortDescription

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Town)) return false

        Town town = o

        if (description != town.description) return false
        if (name != town.name) return false
        if (shortDescription != town.shortDescription) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0)
        return result
    }

    List<PlayerCharacter> getHomes() {
        return PlayerCharacter.findAllByTown(this)
    }
}
