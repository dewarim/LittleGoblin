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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Image)) return false

        Image image = (Image) o

        if (artist != image.artist) return false
        if (description != image.description) return false
        if (height != image.height) return false
        if (license != image.license) return false
        if (name != image.name) return false
        if (sourceUrl != image.sourceUrl) return false
        if (url != image.url) return false
        if (width != image.width) return false

        return true
    }

    int hashCode() {
        int result
        result = (width != null ? width.hashCode() : 0)
        result = 31 * result + (height != null ? height.hashCode() : 0)
        result = 31 * result + (url != null ? url.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (license != null ? license.hashCode() : 0)
        result = 31 * result + (artist != null ? artist.hashCode() : 0)
        return result
    }
}
