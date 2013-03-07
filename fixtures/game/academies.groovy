import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.AcademySkillSet
import de.dewarim.goblin.town.GuildAcademy

include('game/towns')
include('game/guilds')
include('game/dice')
include('game/skillSets')

fixture {
    // Academies for the default town:
    combatAcademy(Academy, name: 'academy.default.combat',
            description: 'academy.default.combat.description',
            town: town)
    craftingAcademy(Academy, name: 'academy.default.crafting', 
            description: 'academy.default.crafting.description', 
            town: town)
    storytellerAcademy(Academy, name: 'academy.default.storyteller', 
            description: 'academy.default.storyteller.description', 
            town: town)
    
    /*
     * The Storyteller Guild has its own Academy:
     */
    guildAcademy(GuildAcademy, guild:storyteller, academy:storytellerAcademy)
    
    /*
     * At the Storyteller's academy, you can learn three stories:
     */
    assLumberjack(AcademySkillSet, academy: storytellerAcademy, skillSet:lumberjack, requiredLevel:1)
    assSweet(AcademySkillSet, academy: storytellerAcademy, skillSet:sweet, requiredLevel:2)
    assGold(AcademySkillSet, academy: storytellerAcademy, skillSet:gold, requiredLevel:3)
    
  
    /*
     * At the Fight academy, a character may learn combat skills:
     */
    assCombat1(AcademySkillSet, academy:combatAcademy, skillSet: combat1, requiredLevel:1)
    assCombat2(AcademySkillSet, academy:combatAcademy, skillSet: combat2, requiredLevel:2)
    assCombat3(AcademySkillSet, academy:combatAcademy, skillSet: combat3, requiredLevel:3)
    
    
    /*
     * At the crafting academy, a character is taught how to produce items.
     */
    assCraft1(AcademySkillSet, academy: craftingAcademy, skillSet: craft1, requiredLevel: 1)
    assCraft2(AcademySkillSet, academy: craftingAcademy, skillSet: craft2, requiredLevel: 2)
    assCraft3(AcademySkillSet, academy: craftingAcademy, skillSet: craft3, requiredLevel: 3)
    
}