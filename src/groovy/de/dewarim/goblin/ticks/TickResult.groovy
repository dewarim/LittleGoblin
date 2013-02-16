package de.dewarim.goblin.ticks

/**
 * Result of a message sent to a ticker actor by the TickerService
 */
class TickResult {

    Boolean failed = false
    List messages = []
}
