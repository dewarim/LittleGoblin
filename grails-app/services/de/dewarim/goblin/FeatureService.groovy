package de.dewarim.goblin;

import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.pc.PlayerCharacter;

/**
 * Supply effects for items.
 * @author ingo
 *
 */
class FeatureService {
	
	void executeFeature(Feature effect, String featureConfig, PlayerCharacter pc, Collection<Creature> mobs, Item item){
		ICombatScript script
		try{
			script = (ICombatScript) effect.script.newInstance();
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		script.execute(pc, mobs, item, featureConfig)
	}
	
}
