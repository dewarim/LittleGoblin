package de.dewarim.goblin.pc.crafting

/**
 *
 */
class ProductCategory {

    static hasMany = [products:Product]

    static constraints = {
        description nullable:true
        name unique: true
    }

    String name
    String description // optional: description
    
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ProductCategory)) return false

        ProductCategory that = o

        if (description != that.description) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return name != null ? name.hashCode() : 0
    }
}
