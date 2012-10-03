package de.dewarim.goblin.mob;

import de.dewarim.goblin.Creature
import de.dewarim.goblin.CreatureAttribute
import de.dewarim.goblin.combat.CombatAttributeType

class Mob extends Creature{

	static belongsTo = [type: MobTemplate]

    static constraints = {
		image(nullable:true)
	}

	Long xpValue = 1
	MobImage image

	void initMob(){
		xpValue = type.xpValue
		strike = type.strike
		parry = type.parry
		initiative = type.initiative
		damage = type.damage
		hp = type.hp
		maxHp = type.hp
		image = type.selectImage()
	}

    

     Map fetchResistanceAttributeMap(Creature opponent){
        Map map = opponent.fetchItemCombatAttributeMap()

         // mobs use the MobTemplate.resistanceAttributes.
        type.resistanceAttributes.each{res ->
              CombatAttributeType cat = res.combatAttributeType
                List modifiers = map.get(cat)
                if(modifiers){
                    map.put(cat, modifiers.add(res.damageModifier))
                }
                else{
                    map.put(cat,[res.damageModifier])
                }
        }
        return map
    }

    Integer addCreatureCombatAttributes(resistanceAttributeMap,dam){
        // use MobTemplate.combatAttributes:
        type.combatAttributes.each {ca ->
            dam = dam * ca.damageModifier
            CombatAttributeType cat = ca.combatAttributeType
            if (resistanceAttributeMap.containsKey(cat)) {
                resistanceAttributeMap.get(cat).each {combatModifier ->
                    dam = dam * resistanceAttributeMap.get(cat)
                }
            }
        }
        return dam
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Mob)) return false

        Mob mob = (Mob) o

        if (image != mob.image) return false
        if (type != mob.type) return false
        if (xpValue != mob.xpValue) return false

        return true
    }

    int hashCode() {
        int result
        result = (xpValue != null ? xpValue.hashCode() : 0)
        result = 31 * result + (image != null ? image.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        return result
    }
}
