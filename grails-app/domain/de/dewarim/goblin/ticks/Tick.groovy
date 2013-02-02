package de.dewarim.goblin.ticks

class Tick {

    static constraints = {
    }
    
    String name
    /**
     * Time between two ticks in milliseconds
     */
    Long tickLength = 60000
    String serviceName
    Long currentTick = 0
    Boolean active = true
}
