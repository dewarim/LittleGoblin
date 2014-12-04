package de.dewarim.goblin

import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.combat.CombatMessage
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.skill.CreatureSkill

abstract class Creature {

    static hasMany = [
            slots               : EquipmentSlot,
            // TODO: combatAttribute and resistanceAttribute need to be separated.
            combatAttributes    : CreatureAttribute,
            resistanceAttributes: CreatureAttribute,
            creatureSkills      : CreatureSkill,
    ]

    static constraints = {
        male nullable: true
        /*
         * Dice for strike, parry, damage, initiative are not necessarily available during
         * creature creation (due to being initialized later on). So we have to make them
         * nullable. (There is also a problem where Hibernate seems to instantiate a test object
         * at start time, which does not work at all if for example a damage dice is missing
         * in the database.)
         */
        strike nullable: true
        parry nullable: true
        damage nullable: true
        initiative nullable: true
        name size: 2..32, blank: false
        description size: 1..1024, blank: true
    }

    String description = ""
    String name

    Integer maxHp = 10
    Integer hp = 10
    Dice strike
    Dice parry
    Dice damage
    Dice initiative
    Long gold = 0
    Boolean male = true

    CombatMessage attack(Creature opponent, Combat combat, boolean saveMessage) {
        CombatMessage cm
        if (computeStrike() > opponent.computeParry()) {
            Integer dam = computeDamage()

            // collect item dice for damage
            def itemTypes = slots.findAll { it.item != null }.collect { it.item.type }
            itemTypes.each { itemType ->
                if (itemType.combatDice) {
                    dam = dam + itemType.combatDice.roll()
                }
            }
            log.debug("combat damage from items vs. ${opponent.name}: ${dam}")
            def resistanceAttributeMap = opponent.fetchResistanceAttributeMap(opponent)
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
            dam = addCreatureCombatAttributes(resistanceAttributeMap, dam)
            log.debug("combat damage after adding creature attributes vs. ${opponent.name}: ${dam}")

            // TODO: refactor combatAttributes and itemAttribute handling.
            cm = new CombatMessage('fight.strike', [name, opponent.name, dam], combat)
            opponent.hp = opponent.hp - dam
        }
        else {
            // TODO: message for block / AR
            cm = new CombatMessage('fight.miss', [name, opponent.name], combat)
        }
        if(saveMessage) {
            cm.save()
        }
        return cm
    }

    /*
     * Note: this may be extended to include other bonuses.
     * This may include temporary combat effects (for example, a spell which increases a skill level).
     * Improvement: cache the results of combatSkills. It is unlikely that the player will
     * gain skills during combat.
     */
    Integer computeStrike() {
        def combatSkills = fetchCombatSkills("strike")
        return computeCombatSkill("strike", combatSkills)
    }

    Integer computeParry() {
        def combatSkills = fetchCombatSkills("parry")
        return computeCombatSkill("parry", combatSkills)
    }

    Integer computeDamage() {
        def combatSkills = fetchCombatSkills("damage")
        return computeCombatSkill("damage", combatSkills)
    }

    Integer computeInitiative() {
        def combatSkills = fetchCombatSkills("initiative")
        return computeCombatSkill("initiative", combatSkills)
    }

    Integer computeCombatSkill(String prop, creatureSkills) {
        Integer temp = this."$prop".roll()
        log.debug("combat value for $prop before skills: $temp")
        creatureSkills.each { cs ->
            cs.level.times {
                temp = temp + cs.skill."$prop".roll()
            }
        }
        log.debug("combat value for $prop including skill: $temp")
        return temp
    }

    Integer addCreatureCombatAttributes(resistanceAttributeMap, dam) {
        combatAttributes.each { ca ->
            dam = dam * ca.damageModifier
            CombatAttributeType cat = ca.combatAttributeType
            if (resistanceAttributeMap.containsKey(cat)) {
                resistanceAttributeMap.get(cat).each { combatModifier ->
                    dam = dam * resistanceAttributeMap.get(cat)
                }
            }
        }
        return dam
    }

    Map fetchResistanceAttributeMap(Creature opponent) {
        Map map = opponent.fetchItemCombatAttributeMap()
        resistanceAttributes.each { res ->
            CombatAttributeType cat = res.combatAttributeType
            List modifiers = map.get(cat)
            if (modifiers) {
                modifiers.add(res.damageModifier)
                map.put(cat, modifiers)
            }
            else {
                map.put(cat, [res.damageModifier])
            }
        }
        return map
    }

    Map<CombatAttributeType, List<Double>> fetchItemCombatAttributeMap() {
        List<ItemType> itemTypes = slots.findAll { it.item != null }.collect { it.item.type }
        Map map = [:]
        itemTypes.each { itemType ->
            itemType.resistanceAttributes.each { ca ->
                CombatAttributeType cat = ca.combatAttributeType
                List modifiers = map.get(cat)
                if (modifiers) {
                    modifiers.add(ca.damageModifier)
                    map.put(cat, modifiers)
                }
                else {
                    map.put(cat, [ca.damageModifier])
                }
            }
        }
        return map
    }

    Boolean equipItem(Item item, Boolean dryRun) {
        log.debug("item requires ${item?.type?.requiredSlots?.size()} slots.")
        Boolean success = true

        for (RequiredSlot rs in item.type.requiredSlots) {
            try {
                for (i in 0..<rs.amount) {
                    log.debug("checking for required slot: ${rs}")
                    EquipmentSlot freeSlot = slots.find { slot ->
                        log.debug("Slot name: ${slot.name} item: ${slot.item} && ${slot.type.equals(rs.slotType)} [rs:${rs.slotType.name}]")
                        (!slot.item) && (slot.type.equals(rs.slotType))
                    }
                    if (!freeSlot) {
                        success = false
                        break
                    }
                    else if (!dryRun) {
                        freeSlot.item = item
                    }

                }
                if (!success) {
                    // do not continue once it's clear that we do not have enough free slots.
                    break
                }
            }
            catch (Exception e) {
                log.debug("", e)
            }
        }
        if (!dryRun && !success) {
            // clean up if equipping failed for some reason.
            log.debug("unequip item: ${item?.dump()}")
            unequipItem(item)
        }
        else if (success) {
            item.equipped = true
        }
        return success
    }

    Boolean equipItem(Item item) {
        return equipItem(item, false)
    }

    void unequipItem(Item item) {
        slots.each { slot ->
            log.debug("unequipItem, test slot ${slot.type.name}")
            Item equippedItem = slot.item
            log.debug("equipped item: ${equippedItem} vs ${item}")
            if (equippedItem && equippedItem.equals(item)) {
                slot.item = null
            }
            log.debug("after equals")
        }
        item.equipped = false
    }

    Collection<CreatureSkill> fetchCombatSkills(String filterAttribute) {
        if (filterAttribute) {
            return CreatureSkill.findAll("from CreatureSkill cs where cs.owner=:owner and cs.skill.id in (select s.id from CombatSkill s) and cs.skill.$filterAttribute != null",
                    [owner: this])
        }
        else {
            return CreatureSkill.findAll("from CreatureSkill cs where cs.owner=:owner and cs.skill.id in (select s.id from CombatSkill s)",
                    [owner: this])
        }
    }

    Collection<CreatureSkill> fetchCombatSkills() {
        return fetchCombatSkills(null)
    }

    Collection<CreatureSkill> fetchProductionSkills() {
        return CreatureSkill.findAll("from CreatureSkill cs where cs.owner=:owner and cs.skill.id in (select c.id from ProductionSkill c)",
                [owner: this])
    }

    /**
     * Check if this creature can equip an item.
     * @param item
     * @return true if the necessary conditions are met, false otherwise
     */
    Boolean canEquipItem(Item item) {
        if (item.equipped) {
            return false
        }
        if (!item.type.requiredSlots?.size() > 0) {
            return false
        }
        return equipItem(item, true)
    }

    List fetchEquipment() {
        return slots.sort { a, b -> a.rank <=> b.rank }
    }

    abstract List<Item> getItems()

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Creature)) return false

        Creature creature = o

        if (damage != creature.damage) return false
        if (description != creature.description) return false
        if (gold != creature.gold) return false
        if (hp != creature.hp) return false
        if (initiative != creature.initiative) return false
        if (male != creature.male) return false
        if (maxHp != creature.maxHp) return false
        if (name != creature.name) return false
        if (parry != creature.parry) return false
        if (strike != creature.strike) return false

        return true
    }

    int hashCode() {
        int result
        result = (description != null ? description.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (maxHp != null ? maxHp.hashCode() : 0)
        result = 31 * result + (hp != null ? hp.hashCode() : 0)
        result = 31 * result + (gold != null ? gold.hashCode() : 0)
        return result
    }
}
