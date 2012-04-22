package de.dewarim.goblin.town;

import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.pc.PlayerCharacter

/**
 * Implements a town (or comparable location) which the player may visit.
 * Currently, travel is not implemented yet.
 */
class Town {
	
	static hasMany = [shops:Shop,
            academies:Academy,
            homes:PlayerCharacter // a player character has a home town.
    ]
	static constraints = {
		name(blank:false, nullable:false)
		shortDescription(blank:false)
		description(nullable:true, blank:false, size:1..4000)
	}

	String name
	String description
	String shortDescription
	
}
