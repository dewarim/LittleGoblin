package de.dewarim.goblin.quest

/**
 * Mapping class QuestStep to QuestStep (n:m parent-child relation).
 * A QuestStep's children are the quest steps following the current step.
 * A QuestStep's parents are the quest steps preceding the current step.
 */
class StepChild {

    static belongsTo = [parent:QuestStep, child:QuestStep]

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
}
