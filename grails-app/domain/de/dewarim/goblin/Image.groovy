package de.dewarim.goblin

import de.dewarim.goblin.mob.MobImage;

/**
 * The base Image class is used for the game's graphical assets like mob pictures,
 * a coat of arms for a guild or the item images. An image must be connected with an Artist
 * instance to identify the copyright holder. It also needs a license so the terms of use
 * for this image are clear.
 */
class Image {

    static belongsTo = [artist:Artist]

    static hasMany = [mobImages:MobImage]

	static constraints = {
		url(blank:false)
		name(blank:false)
		sourceUrl(nullable:true)
		artist(nullable:true)
	}
	
	Integer width = 0
	Integer height = 0
	String url
	String name
	String sourceUrl
	String description
    License license
}
