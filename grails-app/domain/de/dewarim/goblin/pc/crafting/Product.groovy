package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.ComponentType

/**
 *  A product is something which can be crafted by a player.
 */
class Product {

    static final Integer MAX_ITEMS_PER_RUN = 10000

    static hasMany = [components:Component, // components that are consumed during production (and how much)
            requiredSkills:SkillRequirement, // skill needed and their level
            crafters:PlayerProduct // players who know how to make this product
    ]

    static belongsTo = [category:ProductCategory]
    static constraints = {
        name unique:true
//        productionScript nullable:true
    }

    String name
    Long timeNeeded
    // productionScript is disabled at the moment - too much complexity.
//    Class productionScript

    boolean equals(o) {
        if (is(o)) return true

        if (!(o instanceof Product)) return false

        Product product = o

        if (name != product.name) return false
        if( timeNeeded != product.timeNeeded) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }

    Collection<Component> fetchInputItems(){
        return components.findAll{it.type == ComponentType.INPUT}
    }

    Collection<Component> fetchOutputItems(){
        return components.findAll{it.type == ComponentType.OUTPUT}
    }

    Collection<Component> fetchTools(){
        return components.findAll{it.type == ComponentType.TOOL}
    }

    void deleteFully(){
        requiredSkills.each{reqSkill ->
            reqSkill.deleteFully()
        }
        crafters.each{crafter ->
            crafter.deleteFully()
        }
        delete()
    }
}
