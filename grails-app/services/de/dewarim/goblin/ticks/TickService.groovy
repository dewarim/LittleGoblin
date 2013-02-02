package de.dewarim.goblin.ticks

class TickService {

    def grailsApplication
    
    static Map<String, TickActor> tickMap = new HashMap<>() 
    
    def initialize() {
        def ticks = Tick.list()
        ticks.each{tick ->
            def tickActor = new TickActor()
            tickMap.put(tick.name, tickActor)
            tickActor.start()
            TickCommand initCommand = new TickCommand(type: TickCommandType.UPDATE_TICK, tickId: tick.id)
            tickActor.sendAndWait(initCommand)
            TickCommand startCommand = new TickCommand(type: TickCommandType.START_TICKING)
            tickActor.sendAndContinue(startCommand){TickResult result ->
                log.debug("tickResult: "+ result.failed ? 'failed' : 'ok')
            }
        }
    }
}
