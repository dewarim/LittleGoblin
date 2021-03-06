package de.dewarim.goblin.ticks

/**
 * Command object send to a TickMaster by the TickService
 *
 */
class TickCommand {

    TickCommandType type
    Long tickId = 0

    @Override
    public String toString() {
        return "TickCommand{" +
                "type=" + type +
                ", tickId=" + tickId +
                '}';
    }
}
