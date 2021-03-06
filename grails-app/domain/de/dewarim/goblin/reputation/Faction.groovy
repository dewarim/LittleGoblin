package de.dewarim.goblin.reputation

/**
 * A faction is an entity (or group) with which a player character may earn a certain Reputation.
 */
class Faction {

    static hasMany = [playerReputations:Reputation]

    static constraints = {
        repMessageMap nullable:true // so you can create a Faction without having an rmm.
        name blank:false, unique:true
        description blank:false
    }

    String name
    String description
    Integer startLevel = 0
    ReputationMessageMap repMessageMap

    String fetchDescription(Integer level){
        return repMessageMap?.fetchReputationMessage(level)?.messageId ?: "---"
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Faction)) return false

        Faction faction = o

        if (description != faction.description) return false
        if (name != faction.name) return false
        if (repMessageMap != faction.repMessageMap) return false
        if (startLevel != faction.startLevel) return false

        return true
    }

    int hashCode() {
        return name != null ? name.hashCode() : 0
    }
}