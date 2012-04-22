package de.dewarim.goblin.quest;

class QuestTemplate {
	
	static hasMany = [requirements:QuestRequirement, steps:QuestStep]
    static belongsTo = [giver:QuestGiver]
	static constraints = {
		level nullable:false
		name nullable:false, blank:false
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

}
