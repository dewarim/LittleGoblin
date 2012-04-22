package de.dewarim.goblin;

import de.dewarim.goblin.pc.PlayerCharacter;
import de.dewarim.goblin.pc.skill.CreatureSkill;

/**
 *
 */
public interface ISkillScript {

    void execute(PlayerCharacter pc, CreatureSkill creatureSkill, String initParams);

}
