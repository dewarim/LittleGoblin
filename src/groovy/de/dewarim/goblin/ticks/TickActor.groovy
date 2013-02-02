package de.dewarim.goblin.ticks

import groovyx.gpars.actor.DefaultActor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class TickActor extends DefaultActor {

    Logger log = LoggerFactory.getLogger(this.class)

    Long tickId
    Boolean running = true


    protected void act() {
        loop {
            react { command ->
                TickResult result = new TickResult()
                try {
                    switch (command.type) {
                        case TickCommandType.UPDATE_TICK: updateTick(command); break
                        case TickCommandType.START_TICKING: startTicking(); break
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

    def updateTick(command) {
        tickId = command.tickId
    }

    def startTicking() {
        try {
            running = true
            while (running) {
                Tick.withTransaction {
                    Tick tick = Tick.get(tickId)
                    if (!tick) {
                        log.error("Tick $tickId does not exist. Stopping TickActor.")
                        running = false
                        return
                    }
                    def grailsApp = tick.domainClass.grailsApplication
                    def service = grailsApp.getMainContext().getBean("${tick.serviceName}Service")
                    log.debug("service: ${service}")
                    ((ITickListener) service).tock()
                    tick.currentTick++
                    Thread.sleep(tick.tickLength)
                }
            }
        }
        catch (Exception e) {
            log.warn("startTicking failed.", e)
        }
    }

    def stopTicking() {
        running = false
    }

}
