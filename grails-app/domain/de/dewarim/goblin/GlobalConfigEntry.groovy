package de.dewarim.goblin

/**
 * Define global configuration values.
 * A GlobalConfigEntry consists of a unique name and a matching value string.
 * Those entries may be queried via the GlobalConfigService class.
 */
class GlobalConfigEntry {

    static constraints = {
        name unique:true
        entryValue nullable:true, size: 1..1024
        description nullable:true, size: 1..1024
    }

    String name
    String entryValue
    String description

    /**
     Global Entries:
     coins.price.create_order = Coins needed to create a new order.
     order.chatterboxes = Chatterboxes allowed per order.

     */
    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof GlobalConfigEntry)) return false

        GlobalConfigEntry that = o

        if (description != that.description) return false
        if (entryValue != that.entryValue) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (entryValue != null ? entryValue.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        return result
    }
}
