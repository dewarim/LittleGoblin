package de.dewarim.goblin.ticks

/**
 * Simple dummy class which implements ITickListener so it can be used as a bean in the TickSpec test.
 */
class TickTestBean implements ITickListener {
    @Override
    void tock() {
        // nop.
    }
}
