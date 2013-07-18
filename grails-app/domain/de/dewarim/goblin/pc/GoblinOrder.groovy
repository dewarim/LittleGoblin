package de.dewarim.goblin.pc

import de.dewarim.goblin.social.ChatterBox

/**
 *
 */
class GoblinOrder {

    static hasMany = [applications: OrderApplication, chatterBoxes: ChatterBox]
    static constraints = {
        name blank: false, unique: true
        description blank: true
        leader unique: true, nullable: true
    }

    Long score = 0
    String name
    String description
    PlayerCharacter leader
    Long coins = 0

    ChatterBox fetchDefaultChatterBox() {
        return chatterBoxes.find { true } // return the first element.
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
        return (name != null ? name.hashCode() : 0)
    }

    List getMembers() {
        return PlayerCharacter.findAll("from PlayerCharacter pc where pc.goblinOrder=:gob", [gob: this])
    }

    void deleteComplete() {
        PlayerCharacter.executeUpdate(
                "update PlayerCharacter pc set pc.goblinOrder=null where pc.goblinOrder=:goblinOrder",
                [goblinOrder: this]
        )
        applications.each {
            // TODO: inform player about demise of the order.
            it.delete()
        }
        chatterBoxes.each { chatterbox ->
            this.removeFromChatterBoxes(chatterbox)
            chatterbox.chatMessages.each {
                chatterbox.removeFromChatMessages(it)
                it.delete()
            }
            chatterbox.delete()
        }
        this.delete()
    }
}
