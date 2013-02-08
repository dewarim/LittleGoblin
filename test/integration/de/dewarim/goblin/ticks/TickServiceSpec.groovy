package de.dewarim.goblin.ticks

import grails.plugin.spock.IntegrationSpec

/**
 * Test class for TickServiceSpec.
 * This test will work, but afterwards you can expect to see exceptions in the form of:
 * {@code
 * de.dewarim.goblin.ticks.TickRunner Tick failed: [Method on class [de.dewarim.goblin.ticks.Tick] 
 * was used outside of a Grails application. 
 * If running in the context of a test using the mocking API or bootstrap Grails correctly.]
 * }
 * It seems like the test framework has problems with transactions running in GPars Actors.
 * In production use, this code can be observed to work: the actors are called and generate the
 * expected log output. Still, a proper test would be better.
 */
class TickServiceSpec extends IntegrationSpec{
    
    def "observe tickActor to run tick"() {
        setup:
        def tick = Tick.findByBeanName('listenerTestBean')
        def count = tick.currentTick
        tick.tickLength = 130        
        
        when:
        new TickRunner().doTick(new TickCommand(type: TickCommandType.DO_TICK, tickId: tick.id), new TickResult())
        Thread.sleep(200)
        
        then:        
        tick.currentTick == count + 1                
    }
       
}
