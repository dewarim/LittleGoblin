package de.dewarim.goblin

/**
 * Define global configuration values.
 * A GlobalConfigEntry consists of a unique name and a matching value string.
 * Those entries may be queried via the GlobalConfigService class.
 */
class GlobalConfigEntry {

    static constraints = {
        name unique:true, nullable:false
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

}
