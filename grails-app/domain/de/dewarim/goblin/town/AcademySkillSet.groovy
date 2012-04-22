package de.dewarim.goblin.town

import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.pc.skill.LearningQueueElement

/**
 * An Academy offers n SkillSets of a specific level. This class maps
 * academies to skill sets and defines the character level needed to learn
 * the skill set. It implements convenience methods to access the skill sets
 * fields.
 */
class AcademySkillSet {

    static belongsTo = [academy:Academy, skillSet:SkillSet]
    static hasMany = [learningQueueElements:LearningQueueElement]
    Integer requiredLevel = 1

    Long tellRequiredTime(){
        return skillSet.learningTime
    }

    Integer fetchGoldPrice(){
        return skillSet.goldPrice
    }

    Integer fetchXpPrice(){
        return skillSet.xpPrice
    }

    Integer fetchCoinPrice(){
        return skillSet.coinPrice
    }

    boolean equals(o) {
        if (this.is(o)){
            return true;
        }

        if (!(o instanceof AcademySkillSet)){
            return false;
        }

        AcademySkillSet that = (AcademySkillSet) o;

        if (academy != that.academy || skillSet != that.skillSet || requiredLevel != that.requiredLevel){ 
            return false;
        }

        return true;
    }

    int hashCode() {
        return (academy.hashCode() ?: 0) + (skillSet.hashCode() ?: 0);
    }
}
