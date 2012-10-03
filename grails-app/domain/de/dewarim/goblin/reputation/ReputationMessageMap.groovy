package de.dewarim.goblin.reputation

/**
 * The reputation with a given faction is internally an integer value.
 * To the player, those should be presented with a entertaining and
 * descriptive message. 
 */
class ReputationMessageMap {

    static hasMany = [repMessages: ReputationMessage]
    static constraints = {
        faction nullable: true // so you can create a RMM without immediately assigning a faction.
    }

    String name // a messageMap should have a name for better management.
    Faction faction

    ReputationMessage fetchReputationMessage(Integer level) {
        ReputationMessage rm =
        ReputationMessage.find("from ReputationMessage r where r.repMessageMap=:messageMap and r.reputation <= :reputation order by r.reputation - abs(:reputation2) desc",
                [messageMap: this, reputation: level, reputation2:level]
        )
        return rm
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ReputationMessageMap)) return false

        ReputationMessageMap that = (ReputationMessageMap) o

        if (faction != that.faction) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        return result
    }
}
