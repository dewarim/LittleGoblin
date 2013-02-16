package de.dewarim.goblin

import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.combat.CombatMessage
import de.dewarim.goblin.exception.MonsterDeadException
import de.dewarim.goblin.exception.PlayerDeadException
import de.dewarim.goblin.exception.SimultaneousDeathException
import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.pc.PlayerCharacter

class FightService {

    def fight(Combat combat, PlayerCharacter pc, Mob mob) {
//        Combat.withTransaction {
//            combat.merge()
        try{
            if(roll_initiative(pc, mob)){
                combat.addToMessages(pc.attack(mob))
                checkDeath(pc, mob)
                combat.addToMessages(mob.attack(pc))
            }
            else{
                combat.addToMessages(mob.attack(pc))
                checkDeath(pc, mob)
                combat.addToMessages(pc.attack(mob))
            }
            combat.save()
            checkDeath(pc, mob)
        }
        catch(PlayerDeadException pde){
            combat.addToMessages(new CombatMessage('fight.pc.dead', [pc.name]))
            combat.save()
            return "death"
        }
        catch(MonsterDeadException mde){
            combat.addToMessages(new CombatMessage('fight.mob.dead', [mob.name]))
            combat.save()
            return "victory"
        }
        catch(SimultaneousDeathException sde){
            combat.addToMessages(new CombatMessage('fight.all.dead', []))
            combat.save()
            return "death"
        }
        return null
    }

    Boolean roll_initiative(pc, mob){
		return pc.initiative.roll() > mob.initiative.roll()
	}

	void checkDeath(pc, mob){
		if(pc.hp < 0 && mob.hp < 0){
			throw new SimultaneousDeathException()
		}
		else if(pc.hp < 0){
			throw new PlayerDeadException()
		}
		if(mob.hp < 0){
			throw new MonsterDeadException()
		}
	}
}
