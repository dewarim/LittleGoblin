import de.dewarim.goblin.Dice
import de.dewarim.goblin.pc.skill.CombatSkill

def d1 = Dice.findByName('1d1')

fixture{
    basicSkill(CombatSkill, name:'skill.combat.basic',
            strike:d1, parry:d1, damage:d1, initiative:d1             
    )
}