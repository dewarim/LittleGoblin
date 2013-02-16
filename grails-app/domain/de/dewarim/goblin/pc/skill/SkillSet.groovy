package de.dewarim.goblin.pc.skill

/**
 *
 */
class SkillSet {

    static hasMany = [skills:Skill]

    String name
    String description

    Long learningTime
    Integer xpPrice = 1
    Integer goldPrice = 1
    Integer coinPrice = 0

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof SkillSet)) return false

        SkillSet skillSet = o

        if (coinPrice != skillSet.coinPrice) return false
        if (description != skillSet.description) return false
        if (goldPrice != skillSet.goldPrice) return false
        if (learningTime != skillSet.learningTime) return false
        if (name != skillSet.name) return false
        if (xpPrice != skillSet.xpPrice) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (learningTime != null ? learningTime.hashCode() : 0)
        result = 31 * result + (xpPrice != null ? xpPrice.hashCode() : 0)
        result = 31 * result + (goldPrice != null ? goldPrice.hashCode() : 0)
        result = 31 * result + (coinPrice != null ? coinPrice.hashCode() : 0)
        return result
    }
}
