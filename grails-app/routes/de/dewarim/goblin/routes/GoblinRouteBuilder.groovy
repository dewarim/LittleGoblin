package de.dewarim.goblin.routes;

//import org.apache.camel.builder.RouteBuilder
//import org.apache.camel.model.RoutesDefinition
//import org.apache.camel.CamelContext
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 * This class is unused. It was intended to be part of an Apache Camel/ActiveMQ / Quartz - Queue.
 * But due to the current state of those libraries missing documentation I was unable to make it work.
 * [2010-08-08]
 * Camel itself works, but when I enable Quartz, it crashes on startup. If you find a use for
 * non-delayed messages, you may use this here as an example (and the out-commented code in resources.groovy
 * and BootStrap. 
 * ).
 */
@Component
class GoblinRouteBuilder{ // extends RouteBuilder{

   Logger log = LoggerFactory.getLogger(this.class.name)

   def learningDelayer 

   void configure() throws Exception{
       log.debug("GoblinRouteBuilder called.")
       from('quartz://learning/delayTimer?trigger.repeatCount=0&trigger.startTime=10000&stateful=true').
//       delay().method('learningDelayer', 'computeDelay').
       to('bean:learningListenerService?method=eventListener')
   }

}
