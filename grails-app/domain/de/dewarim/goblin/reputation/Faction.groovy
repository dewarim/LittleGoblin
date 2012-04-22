package de.dewarim.goblin.reputation

/**
 * A faction is an entity (or group) with which a player character may earn a certain Reputation.
 */
class Faction {

    static hasMany = [playerReputations:Reputation]

    static constraints = {
        repMessageMap nullable:true // so you can create a Faction without having an rmm.
        name blank:false, nullable: false
        description blank:false, nullable: false
    }

    String name
    String description
    Integer startLevel = 0
    ReputationMessageMap repMessageMap

    String fetchDescription(Integer level){
        return repMessageMap?.fetchReputationMessage(level)?.messageId ?: "---"
    }

}