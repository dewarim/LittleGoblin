package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.ComponentType

/**
 *  A product is something which can be crafted by a player.
 */
class Product {

    static final Integer MAX_ITEMS_PER_RUN = 10000
    
    static hasMany = [
            requiredSkills:SkillRequirement, // skill needed and their level
            crafters:PlayerProduct // players who know how to make this product
    ]

    static belongsTo = [category:ProductCategory]
    static constraints = {
        name unique:true
        timeNeeded validator:{
            return it >= 0
        }
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
        return Component.findAllWhere(type: ComponentType.INPUT, product: this)
    }
    
    Collection<Component> fetchOutputItems(){
        return Component.findAllWhere(type: ComponentType.OUTPUT, product:this)
    }

    Collection<Component> fetchTools(){
        return Component.findAllWhere(type: ComponentType.TOOL, product: this)
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


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeNeeded=" + timeNeeded +
                ", category=" + category.name +
                '}';
    }
}
