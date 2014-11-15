package de.dewarim.goblin

import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.combat.CombatAttributeType
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
                pc.attack(mob, combat)
                checkDeath(pc, mob)
                mob.attack(pc, combat)
            }
            else{
                mob.attack(pc, combat)
                checkDeath(pc, mob)
                pc.attack(mob, combat)
            }
            combat.save()
            checkDeath(pc, mob)
        }
        catch(PlayerDeadException pde){
            new CombatMessage('fight.pc.dead', [pc.name], combat).save()
            combat.save()
            return "death"
        }
        catch(MonsterDeadException mde){
            new CombatMessage('fight.mob.dead', [mob.name], combat).save()
            combat.save()
            return "victory"
        }
        catch(SimultaneousDeathException sde){
            new CombatMessage('fight.all.dead', [], combat).save()
            combat.save()
            return "death"
        }
        return null
    }

    Boolean roll_initiative(pc, mob){
        log.debug("roll initiative for ${pc.name} and ${mob.name}")
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

    CombatMessage attack(Creature attacker, Creature opponent, Combat combat){
        CombatMessage cm
        if(attacker.computeStrike() > opponent.computeParry()){
            Integer dam = attacker.computeDamage()
            
                // collect item dice for damage
            def itemTypes = attacker.slots.findAll { it.item != null }.collect { it.item.type }
                itemTypes.each { itemType ->
                    if (itemType.combatDice) {
                        dam = dam + itemType.combatDice.roll()
                    }
                }
            log.debug("combat damage from items vs. ${opponent.name}: ${dam}")
            def resistanceAttributeMap = attacker.fetchResistanceAttributeMap(opponent)
            // add item combat attributes & resistances
            itemTypes.each {itemType ->
                itemType.combatAttributes.each {ca ->
                    dam = dam * ca.damageModifier
                    CombatAttributeType cat = ca.combatAttributeType
                    if (resistanceAttributeMap.containsKey(cat)) {
                        resistanceAttributeMap.get(cat).each {combatModifier ->
                            dam = dam * resistanceAttributeMap.get(cat)
                        }
                    }
                }
            }

            log.debug("combat damage after adding item attributes vs. ${opponent.name}: ${dam}")
            // add creature combat attributes & resistances
            dam = attacker.addCreatureCombatAttributes(resistanceAttributeMap, dam)
            log.debug("combat damage after adding creature attributes vs. ${opponent.name}: ${dam}")

            // TODO: refactor combatAttributes and itemAttribute handling.
            cm = new CombatMessage('fight.strike', [attacker.name, opponent.name, dam], combat)
            opponent.hp = opponent.hp - dam
        }
        else{
            // TODO: message for block / AR
            cm = new CombatMessage('fight.miss', [attacker.name, opponent.name], combat)
        }
        cm.save()
        return cm
    }
}

