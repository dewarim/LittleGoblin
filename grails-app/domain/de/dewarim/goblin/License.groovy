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

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof License)) return false

        License license = o

        if (description != license.description) return false
        if (name != license.name) return false
        if (url != license.url) return false

        return true
    }

    int hashCode() {
        return  name != null ? name.hashCode() : 0
    }
}
