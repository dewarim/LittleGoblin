package de.dewarim.goblin.quest

class QuestStep {

    static belongsTo = [encounter: Encounter, questTemplate: QuestTemplate]
    static hasMany = [nextSteps: StepChild, parentSteps: StepChild]
    static mappedBy = [parentSteps: 'child', nextSteps: 'parent']
    static constraints = {
        title blank: false
        description blank: false
        intro nullable: true
        name unique: true
    }

    /*
      *  Chance of this step being applied.
      *  For example, if one of 10 orcish guards is not at his post, the character may
      *  not encounter him at all...
      */
    Integer chance = 100
    Boolean endOfQuest = false
    Boolean automaticTransition = false

    Boolean descriptionContainsHtml = false

    /**
     * If this QuestStep is the first part of a Quest, set this to true.
     */
    Boolean firstStep = false

    /**
     * Internal name
     */
    String name
    String title
    String description
    String intro

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof QuestStep)) return false

        QuestStep questStep = o

        if (automaticTransition != questStep.automaticTransition) return false
        if (chance != questStep.chance) return false
        if (description != questStep.description) return false
        if (endOfQuest != questStep.endOfQuest) return false
        if (firstStep != questStep.firstStep) return false
        if (intro != questStep.intro) return false
        if (title != questStep.title) return false
        if (descriptionContainsHtml != questStep.descriptionContainsHtml) return false

        return true
    }

    int hashCode() {
        int hash =  (title != null ? title.hashCode() : 0)
        hash = hash * 31 + name.hashCode()
        return hash
    }
}
