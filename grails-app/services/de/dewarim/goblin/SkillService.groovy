package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.skill.CreatureSkill

import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.town.AcademyLevel
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.AcademySkillSet
import de.dewarim.goblin.pc.skill.LearningQueueElement

class SkillService {

    static transactional = true

    void teach(PlayerCharacter pc, SkillSet skillSet){

        skillSet.skills.each{skill ->
            log.debug("type: ${skill.class.name}")
            CreatureSkill theSkill = CreatureSkill.findWhere(owner:pc, skill:skill)

            // if the skill exists, simply raise its level. Otherwise, create it.
            if(theSkill){
                theSkill.level++
            }
            else{
                theSkill = new CreatureSkill(owner:pc, skill:skill)
                pc.addToCreatureSkills(theSkill)
                skill.addToCreatureSkills(theSkill)
                theSkill.save()
            }

            // if a script for this type of skill exists, execute it.
            if(skill.script){
                ISkillScript iss = null
                try {
                    iss = (ISkillScript) skill.script.newInstance();
                }
                catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                iss.execute(pc, theSkill, skill.initParams)
            }

            skill.initSkill(pc)
        }

    }

    Integer checkFinishedSkills (){
        def finishedSkills = LearningQueueElement.findAll("from LearningQueueElement as l where l.finished < now()")

        log.debug("found ${finishedSkills.size()} skills.")

        finishedSkills.each{element ->

            PlayerCharacter pc = element.pc
            AcademySkillSet ass = element.academySkillSet           
            Academy academy = ass.academy


            // raise academy level so at next visit to the academy this skill is not shown again.
            AcademyLevel al = AcademyLevel.findWhere(pc:pc, academy:academy)
            al.level++

            // compute effect of learning this skill:
            teach(pc, ass.skillSet)

            // remove from LearningQueue:
            pc.removeFromLearningQueueElements element
            ass.removeFromLearningQueueElements element
            element.delete()

            log.debug("Player ${pc.name} learned ${ass.skillSet.name}");
        }

        return finishedSkills.size()
    }
}
