package de.dewarim.goblin.pc.skill

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.town.AcademySkillSet

class LearningQueueElement extends QueueElement{

    static belongsTo = [pc:PlayerCharacter, academySkillSet:AcademySkillSet]
    static mapping = {
        version:false
    }
    
    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof LearningQueueElement)) return false

        LearningQueueElement that = (LearningQueueElement) o

        if (academySkillSet != that.academySkillSet) return false
        if (pc != that.pc) return false

        return true
    }

    int hashCode() {
        int result
        result = (academySkillSet != null ? academySkillSet.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        return result
    }
}
