package de.dewarim.goblin.reputation

/**
 * The reputation with a given faction is internally an integer value.
 * To the player, those should be presented with a entertaining and
 * descriptive message.
 */
class ReputationMessageMap {

    static hasMany = [repMessages: ReputationMessage]
    static constraints = {
        
    }

    String name // a messageMap should have a name for better management.

    ReputationMessage fetchReputationMessage(Integer level) {
        ReputationMessage rm =
        ReputationMessage.find("from ReputationMessage r where r.repMessageMap=:messageMap and r.reputation <= :reputation order by r.reputation - abs(:reputation2) desc",
                [messageMap: this, reputation: level, reputation2:level]
        )
        return rm
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ReputationMessageMap)) return false

        ReputationMessageMap that = o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        return result
    }
}
