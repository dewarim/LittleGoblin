package de.dewarim.goblin.item.script;

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.ICombatScript
import de.dewarim.goblin.Creature
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.PlayerMessageService
import org.codehaus.groovy.grails.commons.ApplicationHolder

class HealSelf_1W6 implements ICombatScript{

	void execute(PlayerCharacter pc, Collection<Creature> mobs, Item item, String featureConfig){
		Integer hp = (Integer) ((Math.random() * 6)+1)
		Integer old = pc.hp
		if(pc.hp + hp < pc.maxHp ){
			pc.hp = pc.hp+hp
		}
		else{
			pc.hp = pc.maxHp
		}
		getPMS().createMessage(pc, 'feature.heal_yourself', [pc.hp-old])
	}

     PlayerMessageService getPMS(){
        def ctx = ApplicationHolder.getApplication().getMainContext()
        return (PlayerMessageService) ctx.getBean('playerMessageService')
    }
}
