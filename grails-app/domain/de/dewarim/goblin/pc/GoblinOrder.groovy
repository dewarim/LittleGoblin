package de.dewarim.goblin.pc

import de.dewarim.goblin.social.ChatterBox

/**
 *
 */
class GoblinOrder {

    static hasMany = [members:PlayerCharacter, applications:OrderApplication, chatterBoxes:ChatterBox]
    static constraints = {
        name blank: false, unique: true
        description blank:true
    }

    Long score = 0
    String name
    String description
    PlayerCharacter leader
    Long coins = 0

    ChatterBox fetchDefaultChatterBox(){
        return chatterBoxes.find{true} // return the first element.
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof GoblinOrder)) return false

        GoblinOrder that = o

        if (coins != that.coins) return false
        if (description != that.description) return false
        if (leader != that.leader) return false
        if (name != that.name) return false
        if (score != that.score) return false

        return true
    }

    int hashCode() {
        int result
        result = (score != null ? score.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (leader != null ? leader.hashCode() : 0)
        result = 31 * result + (coins != null ? coins.hashCode() : 0)
        return result
    }
}
