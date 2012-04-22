package de.dewarim.goblin;

import de.dewarim.goblin.item.Item;
import de.dewarim.goblin.pc.PlayerCharacter;

import java.util.Collection;

public interface ICombatScript {

	void execute(PlayerCharacter pc, Collection<Creature> mobs, Item item, String featureConfig);

}
