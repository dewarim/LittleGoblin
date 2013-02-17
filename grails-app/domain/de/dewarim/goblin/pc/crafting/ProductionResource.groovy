package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.item.Item

/**
 * Mapping between a ProductionJob and an item that a player has selected as an input resource.
 */
class ProductionResource {

    static belongsTo = [job:ProductionJob, item:Item]
    Integer amount = 1

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ProductionResource)) return false

        ProductionResource that = o

        if (amount != that.amount) return false
        if (item != that.item) return false
        if (job != that.job) return false

        return true
    }

    int hashCode() {
        int result
        result = (amount != null ? amount.hashCode() : 0)
        result = 31 * result + (item != null ? item.hashCode() : 0)
        result = 31 * result + (job != null ? job.hashCode() : 0)
        return result
    }
}
