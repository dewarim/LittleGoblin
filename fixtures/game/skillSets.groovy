import de.dewarim.goblin.pc.skill.SkillSet

include('game/combatSkills')
include('game/productionSkills')

fixture{
    
    lumberjack(SkillSet, name:'skillSet.fairy.lumberjack', description:'skillSet.fairy.default', learningTime:60000)
    sweet(SkillSet, name:'skillSet.fairy.sweet', description:'skillSet.fairy.default', learningTime:60000)
    gold(SkillSet, name:'skillSet.fairy.gold', description:'skillSet.fairy.default', learningTime:60000)
  
    combat1(SkillSet, name:'skillSet.combat.fight.1', description: 'skillSet.combat.default',
            learningTime:60000, skills:[basicSkill])
    combat2(SkillSet, name:'skillSet.combat.fight.2', description: 'skillSet.combat.default',
            learningTime:60000,  skills:[basicSkill])
    combat3(SkillSet, name:'skillSet.combat.fight.3', description: 'skillSet.combat.default',
            learningTime:60000, skills:[basicSkill])
    
    craft1(SkillSet, name:'skillSet.production.craft1', description:'skillSet.production.default', 
            learningTime:10000, skills:[psIron])
    craft2(SkillSet, name:'skillSet.production.craft2', description:'skillSet.production.default', 
            learningTime:10000, skills:[psSword])
    craft3(SkillSet, name:'skillSet.production.craft3', description:'skillSet.production.default', 
            learningTime:10000, skills:[psShield])
    
}