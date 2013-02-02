package de.dewarim.goblin.ticks

/**
 * Command object send to a TickActor by the TickService 
 * 
 */
class TickCommand {
    
    TickCommandType type
    Long tickId = 0
    
}
