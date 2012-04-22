package de.dewarim.goblin.pc.skill

/**
 *
 */
class SkillSet {

    static hasMany = [skills:Skill  ]

    static constraints = {
       
    }

    String name
    String description

    Long learningTime
    Integer xpPrice = 1
    Integer goldPrice = 1
    Integer coinPrice = 0

}
