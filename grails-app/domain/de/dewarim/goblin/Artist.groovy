package de.dewarim.goblin;

/**
 * The Artist class is used to keep track of who contributed graphical assets to the game.
 * While some games may just use one web designer and graphic talent, others will use many
 * and need to attribute the provided objects correctly.<br>
 * An Artist has a name and may have a website to which a link can be provided.
 */
class Artist {

    static hasMany = [images:Image]

    static constraints = {
		name(nullable:false, blank:false)
		website(nullable:true)
	}

	String name
	String website
	
}
