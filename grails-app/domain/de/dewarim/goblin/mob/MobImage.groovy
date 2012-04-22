package de.dewarim.goblin.mob;

import de.dewarim.goblin.Image
import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Mapping class between MobTemplate and Image.
 */
class MobImage {
	
	static belongsTo = [mobTemplate:MobTemplate, image:Image]

    MobImage(){}

    MobImage(MobTemplate mobTemplate, Image image){
        this.mobTemplate = mobTemplate
        this.image = image
        mobTemplate.addToMobImages this
        image.addToMobImages this
    }

    void deleteFully(){
        mobTemplate.removeFromMobImages this
        image.removeFromMobImages this
        this.delete()
    }

    boolean equals(other) {
		if (!(other instanceof MobImage)) {
			return false
		}

		other.image?.id == image?.id &&
			other.mobTemplate?.id == mobTemplate?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (mobTemplate) builder.append(mobTemplate.id)
		if (image) builder.append(image.id)
		builder.toHashCode()
	}
	
}
