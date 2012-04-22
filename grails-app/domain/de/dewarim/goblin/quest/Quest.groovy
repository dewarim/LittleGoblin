package de.dewarim.goblin.quest;
import de.dewarim.goblin.pc.PlayerCharacter

class Quest {

    static belongsTo = [template: QuestTemplate, playerCharacter: PlayerCharacter]
    static constraints = {
        start nullable: false
        finishedDate nullable: true
        currentStep nullable: true
        lastExecutedStep nullable: true
    }


    Date start = new Date()
    Date finishedDate
    QuestStep currentStep
    QuestStep lastExecutedStep
    Boolean successful = false
    Boolean finished = false

    void initializeQuest() {
        currentStep = template.findFirstStep()
    }

    /**
     * Find the next QuestStep or, if no more steps are available, set the
     * quest to finished and return null.
     * @return the next QuestStep - or null.
     */
    QuestStep fetchNextQuestStep() {
        QuestStep nextStep = null
        if (currentStep && !currentStep.endOfQuest) {
            nextStep = currentStep.nextSteps?.find {true}?.child
        }
        if (!nextStep) {
            // no more steps left: set finishedDate
            finishedDate = new Date()
            finished = true
        }
        return nextStep
    }

    void continueQuest(String nextStepName) {
        log.debug("looking for next questStep: $nextStepName")
        QuestStep qs = currentStep
        StepChild nextStepChild = qs.nextSteps.find {it.child.title.equals(nextStepName)}
        QuestStep nextStep = nextStepChild.child
        if (nextStep) {
            currentStep = nextStep
        }
        else {
            throw new RuntimeException("Could not find next quest step $nextStepName for quest ${template.name}")
        }
    }

    /**
     * Check if the given QuestStep is contained in the current QuestStep's list of
     * subsequent quest steps.
     * @return true if the given step is a valid QuestStep for the current step.
     */
    Boolean verifyNextStep(QuestStep nextStep) {
        QuestStep qs = currentStep?.nextSteps?.find{it.child.equals(nextStep)}?.child
        return qs != null
    }
}
