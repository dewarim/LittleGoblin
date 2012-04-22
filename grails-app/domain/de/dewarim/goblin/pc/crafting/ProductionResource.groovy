package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.item.Item

/**
 * Mapping between a ProductionJob and an item that a player has selected as an input resource.
 */
class ProductionResource {

    static belongsTo = [job:ProductionJob, item:Item]
    Integer amount = 1
}
