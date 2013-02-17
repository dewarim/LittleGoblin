package de.dewarim.goblin.ticks

import groovyx.gpars.actor.DynamicDispatchActor

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class TickRunner extends DynamicDispatchActor {

    Logger log = LoggerFactory.getLogger(this.class)

    void onMessage(TickCommand command){
        try{
            reply doTick(command, new TickResult())
        }
        catch (Exception e){
            log.warn("TickRunner failed.",e)
        }
    }

    TickResult doTick(TickCommand command, TickResult result) {
        try {
            Tick.withTransaction {
                Tick tick = Tick.get(command.tickId)
                if (!tick) {
                    log.error("Tick ${command.tickId} does not exist.")
                    result.failed = true
                    result.messages.add('error.tick.not.found')
                }

                def listener = tick.fetchListener()
                ((ITickListener) listener).tock()
                tick.currentTick++
                Thread.sleep(tick.tickLength)
            }
        }
        catch (Exception e) {
            result.failed = true
            result.messages.add(e.message)
            log.warn("doTick failed:", e)
        }
        result
    }

}
