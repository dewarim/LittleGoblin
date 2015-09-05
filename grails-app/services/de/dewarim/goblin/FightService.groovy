package de.dewarim.goblin

import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.combat.CombatMessage
import de.dewarim.goblin.fight.FightResult
import de.dewarim.goblin.fight.FightResultType
import de.dewarim.goblin.mob.Mob
import de.dewarim.goblin.pc.PlayerCharacter

class FightService {

    def fight(Combat combat, PlayerCharacter pc, Mob mob) {
        if (roll_initiative(pc, mob)) {
            pc.attack(mob, combat, true)
            def dead = checkDeath(pc, mob)
            if (dead.isEmpty()) {
                mob.attack(pc, combat, true)
            }
        }
        else {
            mob.attack(pc, combat, true)
            def dead = checkDeath(pc, mob)
            if (dead.isEmpty()) {
                pc.attack(mob, combat, true)
            }
        }
        combat.save()

        def dead = checkDeath(pc, mob)
        if (dead.isEmpty()) {
            return new FightResult(type: FightResultType.CONTINUE, opponent: mob)
        }
        if (dead.size() == 2) {
            new CombatMessage('fight.all.dead', [], combat).save()
            return new FightResult(type: FightResultType.DEATH, opponent: mob)
        }
        if (dead.contains(pc)) {
            new CombatMessage('fight.pc.dead', [pc.name], combat).save()
            return new FightResult(type: FightResultType.DEATH, opponent: mob)
        }
        else {
            new CombatMessage('fight.mob.dead', [mob.name], combat).save()
            return new FightResult(type: FightResultType.VICTORY, opponent: mob)
        }
    }

    Boolean roll_initiative(pc, mob) {
        log.debug("roll initiative for ${pc.name} and ${mob.name}")
        return pc.initiative.roll() > mob.initiative.roll()
    }

    List<Creature> checkDeath(PlayerCharacter pc, Mob mob) {
        if (pc.dead() && mob.dead()) {
            return [pc, mob]
        }
        else if (pc.dead()) {
            return [pc]
        }
        if (mob.dead()) {
            return [mob]
        }
        return []
    }

    CombatMessage attack(Creature attacker, Creature opponent, Combat combat) {
        CombatMessage cm
        if (attacker.computeStrike() > opponent.computeParry()) {
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
            itemTypes.each { itemType ->
                itemType.combatAttributes.each { ca ->
                    dam = dam * ca.damageModifier
                    CombatAttributeType cat = ca.combatAttributeType
                    if (resistanceAttributeMap.containsKey(cat)) {
                        resistanceAttributeMap.get(cat).each { combatModifier ->
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
            opponent.dealDamage(dam)
        }
        else {
            // TODO: message for block / AR
            cm = new CombatMessage('fight.miss', [attacker.name, opponent.name], combat)
        }
        cm.save()
        return cm
    }
}

