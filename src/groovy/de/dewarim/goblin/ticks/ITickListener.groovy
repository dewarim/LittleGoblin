package de.dewarim.goblin.ticks

/**
 * Interface for classes which want to receive ticks (signals to advance the game state)
 */
interface ITickListener {

    void tock()
}