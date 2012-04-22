package de.dewarim.goblin;

import de.dewarim.goblin.item.ItemTypeFeature;

/**
 * Features allow Items to have scripts associated with them which the user can execute by activating
 * the item. For example, a healing potion will have a healing feature which upon execution adds
 * one or more health points to a player character.<br>
 * This class enables the creation of item types which
 * A Feature object defines a reference to a script class which implements the ICombatScript interface.
 * At the moment, items may be activated only during combat. An item with an associated Feature will have
 * a link or button to click upon which is labeled with the I18n-ed name of the feature. Once the link
 * is clicked, the script is executed and creates the desired effect (if appropriate in the current situation,
 * for example, a drain life spell should not work on an undead monster).
 */
class Feature {
	
	static hasMany = [itemTypeFeatures:ItemTypeFeature]

//    Class<? extends ICombatScript> script // script class to generate the effect
    Class script
	String internalName 
	// internal name, for example to differentiate between "heal 1w6" and "heal 3w6"
	
	String name // screen name (message-Id), to label a heal_1w6-item as "Lesser potion of healing"
}