package de.dewarim.goblin.item

import de.dewarim.goblin.UserProperty

class ItemAttributeCondition {
	
    // Work in Progress
    
	UserProperty userProperty
	
	/**
	 * ConditionScript is a groovy script which receives the Character and the currently checked
	 * item as params.
	 */
	String conditionScriptFile

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ItemAttributeCondition)) return false

        ItemAttributeCondition that = (ItemAttributeCondition) o

        if (conditionScriptFile != that.conditionScriptFile) return false
        if (userProperty != that.userProperty) return false

        return true
    }

    int hashCode() {
        int result
        result = (userProperty != null ? userProperty.hashCode() : 0)
        result = 31 * result + (conditionScriptFile != null ? conditionScriptFile.hashCode() : 0)
        return result
    }
}
