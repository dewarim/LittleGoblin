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
}
