package de.dewarim.goblin;

import de.dewarim.goblin.pc.PlayerCharacter;
import de.dewarim.goblin.quest.Quest;

/**
 *
 */
public interface IEncounterScript {

    void execute(PlayerCharacter pc, Quest quest, String params);
}
