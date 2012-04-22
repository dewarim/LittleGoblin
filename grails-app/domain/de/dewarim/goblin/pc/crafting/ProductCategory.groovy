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

}
