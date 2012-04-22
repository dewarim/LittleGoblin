package de.dewarim.goblin.town

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * Maps the level a player character has achieved in a specific academy.
 * Depending on the level, different skill-sets are offered for learning.
 */
class AcademyLevel {

    static belongsTo = [pc:PlayerCharacter, academy:Academy]
    Integer level = 1
}
