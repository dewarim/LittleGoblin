package de.dewarim.goblin

/**
 * Describes the license of an asset, for example an image.
 */
class License {

    static constraints = {
        url nullable:true
        description nullable:true
        name unique:true
    }

    String name
    String url
    String description
}
