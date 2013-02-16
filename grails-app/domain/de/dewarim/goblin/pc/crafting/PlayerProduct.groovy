package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.pc.PlayerCharacter

class PlayerProduct {

    /*
     * Mapping between a PlayerCharacter and a Product.
     * Essentially, this is to establish which products a PC may create without going through
     * all the requiredSkills etc each time.
     * Also, this allows to grant special crafting capabilities to a PC who may
     * not have the required skills - for example, during a quest the PC learns to make a potion of healing
     * but does not learn the skill "potion mastery" which would allow him to make any kind of potion.
     *
     */

    static belongsTo = [pc:PlayerCharacter, product:Product]


    String toString(){
        return "${pc.name}::${product.name}"
    }

    /**
     * Remove the PlayerProduct from both ends of the relations (PlayerCharacter and Product) and delete it.
     */
    void deleteFully(){
        pc.removeFromPlayerProducts(this)
        product.removeFromCrafters(this)
        delete()
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof PlayerProduct)) return false

        PlayerProduct that = o

        if (pc != that.pc) return false
        if (product != that.product) return false

        return true
    }

    int hashCode() {
        int result
        result = (product != null ? product.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        return result
    }
}
