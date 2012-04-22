package de.dewarim.goblin.quest

/**
 *
 */
class QuestGiver {

    static hasMany = [templates:QuestTemplate]
    String name
    String description
}
