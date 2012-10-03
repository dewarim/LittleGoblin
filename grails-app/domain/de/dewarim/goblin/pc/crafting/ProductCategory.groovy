package de.dewarim.goblin.pc.crafting

/**
 *
 */
class ProductCategory {

    static hasMany = [products:Product]

    static constraints = {
        description nullable:true
    }

    String name
    String description // optional: description
    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof ProductCategory)) return false

        ProductCategory that = (ProductCategory) o

        if (description != that.description) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        return result
    }
}
