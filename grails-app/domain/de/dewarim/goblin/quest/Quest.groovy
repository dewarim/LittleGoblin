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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Quest)) return false

        Quest quest = (Quest) o

        if (currentStep != quest.currentStep) return false
        if (finished != quest.finished) return false
        if (finishedDate != quest.finishedDate) return false
        if (lastExecutedStep != quest.lastExecutedStep) return false
        if (playerCharacter != quest.playerCharacter) return false
        if (start != quest.start) return false
        if (successful != quest.successful) return false
        if (template != quest.template) return false

        return true
    }

    int hashCode() {
        int result
        result = (start != null ? start.hashCode() : 0)
        result = 31 * result + (finishedDate != null ? finishedDate.hashCode() : 0)
        result = 31 * result + (currentStep != null ? currentStep.hashCode() : 0)
        result = 31 * result + (lastExecutedStep != null ? lastExecutedStep.hashCode() : 0)
        result = 31 * result + (successful != null ? successful.hashCode() : 0)
        result = 31 * result + (finished != null ? finished.hashCode() : 0)
        result = 31 * result + (template != null ? template.hashCode() : 0)
        result = 31 * result + (playerCharacter != null ? playerCharacter.hashCode() : 0)
        return result
    }
}
