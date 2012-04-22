package de.dewarim.goblin.item

import de.dewarim.goblin.UserProperty

class ItemAttributeCondition {
	
	UserProperty userProperty
	
	/**
	 * ConditionScript is a groovy script which receives the Character and the currently checked
	 * item as params.
	 */
	String conditionScriptFile
	
}
