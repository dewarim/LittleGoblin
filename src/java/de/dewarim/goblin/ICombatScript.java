package de.dewarim.goblin;

import java.util.Collection;

import de.dewarim.goblin.item.Item;
import de.dewarim.goblin.pc.PlayerCharacter;

public interface ICombatScript {
    
    void execute(PlayerCharacter pc, Collection<Creature> mobs, Item item, String featureConfig);

}
