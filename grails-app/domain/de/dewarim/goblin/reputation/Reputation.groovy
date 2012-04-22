package de.dewarim.goblin.reputation

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * Mapping class between PlayerCharacter and Faction.
 */
class Reputation {

    static belongsTo = [pc:PlayerCharacter, faction:Faction]

    Integer level = 0
    
}
