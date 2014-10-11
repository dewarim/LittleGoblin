package de.dewarim.goblin.ticks

class TickService {

    static Map<String, TickMaster> tickMap = [:]

    def initialize() {
        def ticks = Tick.list()
        ticks.each { tick ->
            def tickMaster = new TickMaster()
            tickMap.put(tick.name, tickMaster)
            startTicking(tickMaster, tick)
        }
    }

    def stopAll() {
        log.debug("Sending stop signal to Ticks.")
        tickMap.values().each { tickMaster ->
            tickMaster.stop()
        }
    }
    
    def terminateAll(){
        log.debug("Sending terminate signal to Actors")
        tickMap.values().each{ tickMaster ->
            tickMaster.terminate()
        }
    }
    
    def startAll() {
        tickMap.each { name, tickMaster ->
            def tick = Tick.findByName(name)
            if (tick.active && ! tickMaster.isActive()) {
                tickMaster.start()
                TickCommand startCommand = new TickCommand(type: TickCommandType.START_TICKING, tickId: tick.id)
                log.debug("send start_ticking command.")
                tickMaster.sendAndContinue(startCommand) { TickResult result ->
                    log.debug("startTicking: " + (result.failed ? 'failed' : 'ok'))
                }
            }
        }
    }

    def stopTicking(tickMaster) {
        log.debug("Stopping tickMaster.")
        TickCommand stopCommand = new TickCommand(type: TickCommandType.STOP_TICKING)
        tickMaster.sendAndContinue(stopCommand) { TickResult result ->
            log.debug("... stopped: ${!result.failed}")
        }
    }

    def startTicking(TickMaster tickMaster, Tick tick) {
        log.debug("startTickMaster")
        if (!tickMaster.isActive()) {
            log.debug("tickMaster is not active, starting")
            tickMaster.start()
        }
        log.debug("started")
        TickCommand startCommand = new TickCommand(type: TickCommandType.START_TICKING, tickId: tick.id)
        tickMap.put(tick.name, tickMaster)
        log.debug("send start_ticking command.")
        tickMaster.sendAndContinue(startCommand) { TickResult result ->
            log.debug("startTicking: " + (result.failed ? 'failed' : 'ok'))
        }
    }
}
