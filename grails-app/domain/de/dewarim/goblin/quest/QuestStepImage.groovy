package de.dewarim.goblin.quest

import de.dewarim.goblin.Image

/**
 * Mapping between QuestSteps and Images
 */
class QuestStepImage {

    static mapping = {
        version(false)
    }

    static constraints = {
        questStep unique: 'image'
    }

    QuestStep questStep
    Image image

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof QuestStepImage)) return false

        QuestStepImage that = (QuestStepImage) o

        if (image != that.image) return false
        if (questStep != that.questStep) return false

        return true
    }

    int hashCode() {
        int result
        result = questStep.hashCode()
        result = 31 * result + image.hashCode()
        return result
    }
}
