package de.dewarim.goblin.quest

/**
 * Mapping class QuestStep to QuestStep (n:m parent-child relation).
 * A QuestStep's children are the quest steps following the current step.
 * A QuestStep's parents are the quest steps preceding the current step.
 */
class StepChild {

    static belongsTo = [parent:QuestStep, child:QuestStep]

    static mapping = {
        version: false
    }

    StepChild(){}

    StepChild(QuestStep parent, QuestStep child){
        this.parent = parent
        this.child = child
        parent.addToNextSteps this
        child.addToParentSteps this
    }

    void deleteFully(){
        parent.removeFromNextSteps this
        child.removeFromParentSteps this
        delete()
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof StepChild)) return false

        StepChild stepChild = o

        if (child != stepChild.child) return false
        if (parent != stepChild.parent) return false

        return true
    }

    int hashCode() {
        int result
        result = (child != null ? child.hashCode() : 0)
        result = 31 * result + (parent != null ? parent.hashCode() : 0)
        return result
    }
}
