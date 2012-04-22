package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.town.AcademySkillSet

class LearningQueueElement extends QueueElement{

    static belongsTo = [pc:PlayerCharacter, academySkillSet:AcademySkillSet]
    static mapping = {
//        academySkillSet lazy:false
//        pc lazy:false
    }

}
