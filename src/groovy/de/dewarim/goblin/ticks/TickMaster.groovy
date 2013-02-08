package de.dewarim.goblin.ticks

import groovyx.gpars.actor.DefaultActor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class TickMaster extends DefaultActor {

    Logger log = LoggerFactory.getLogger(this.class)

    Long tickId
    Boolean running = true
    TickRunner tickRunner

    protected void act() {
        loop {
            react { TickCommand command ->
                TickResult result = new TickResult()
                try {
                    switch (command.type) {
                        case TickCommandType.START_TICKING: startTicking(command); break
                        case TickCommandType.STOP_TICKING: stopTicking(); break
                    }
                    reply result
                }
                catch (Exception e) {
                    log.debug("Failed to act on command: ${command?.dump()}", e)
                    result.failed = true
                    result.messages.add(e.message)
                    reply result
                }
            }
        }
    }

    def startTicking(TickCommand command) {
        try {
            running = true
            if (! tickRunner){
                tickRunner = new TickRunner()
                tickRunner.start()
            }
            TickCommand cmd = new TickCommand(type: TickCommandType.DO_TICK, tickId: command.tickId )
            sendTick(cmd)
        }
        catch (Exception e) {
            log.warn("startTicking failed.", e)
        }
    }
    
    def sendTick(TickCommand cmd){
        if (running){
            TickCommand tc = new TickCommand(type: TickCommandType.DO_TICK, tickId: cmd.tickId)
            tickRunner.sendAndContinue(tc){TickResult result ->
                if (result.failed){
                    log.warn("Tick failed: ${result.messages}")
                }
                else{
//                    log.debug("tick-tock.")
                    sendTick(tc)
                }
            } 
        }
    }

    def stopTicking() {
        running = false
    }

}
