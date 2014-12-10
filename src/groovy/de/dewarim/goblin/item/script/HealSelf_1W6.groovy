package de.dewarim.goblin.item.script

import de.dewarim.goblin.Creature
import de.dewarim.goblin.ICombatScript
import de.dewarim.goblin.PlayerMessageService
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.pc.PlayerCharacter

class HealSelf_1W6 implements ICombatScript{

	void execute(PlayerCharacter pc, Collection<Creature> mobs, Item item, String featureConfig){
		Integer hp = (Math.random() * 6)+1
		int diff = pc.addLife(hp)
		getPMS(pc).createMessage(pc, 'feature.heal_yourself', [diff])
	}

     PlayerMessageService getPMS(pc){
        return pc.domainClass.grailsApplication.mainContext.playerMessageService
    }
}
