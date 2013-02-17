package de.dewarim.goblin.quest

class QuestTemplate {

	static hasMany = [requirements:QuestRequirement, steps:QuestStep]
    static belongsTo = [giver:QuestGiver]
	static constraints = {
		name blank:false
		description size:1..2000
	}

	String description
	String name
	Long level

    /**
     * A quest template that is available to players is marked as active. If you want to delete a quest template,
     * you should mark it as active=false and wait for the current quests depending on it to be finished.
     */
    Boolean active = true

    QuestStep findFirstStep(){
        return steps.find{it.firstStep}
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof QuestTemplate)) return false

        QuestTemplate that = o

        if (active != that.active) return false
        if (description != that.description) return false
        if (giver != that.giver) return false
        if (level != that.level) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (description != null ? description.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (level != null ? level.hashCode() : 0)
        result = 31 * result + (active != null ? active.hashCode() : 0)
        result = 31 * result + (giver != null ? giver.hashCode() : 0)
        return result
    }
}
