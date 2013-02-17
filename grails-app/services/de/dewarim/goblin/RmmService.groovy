package de.dewarim.goblin

import de.dewarim.goblin.reputation.Faction
import de.dewarim.goblin.reputation.ReputationMessage
import de.dewarim.goblin.reputation.ReputationMessageMap

/**
 *
 */
class RmmService {

    void removeFaction(ReputationMessageMap rmm) {
        rmm.faction?.repMessageMap = null
        rmm.faction?.save()
        rmm.faction = null
        rmm.save()
    }

    void addFaction(ReputationMessageMap rmm, Faction faction) {
        // add new faction:
        rmm.faction = faction
        faction.repMessageMap = rmm
        faction.save()
    }

    void removeMessage(ReputationMessage repMessage) {
        ReputationMessageMap rmm = repMessage.repMessageMap
        rmm.removeFromRepMessages repMessage
        repMessage.repMessageMap = null
        repMessage.delete(flush:true)
    }
}
