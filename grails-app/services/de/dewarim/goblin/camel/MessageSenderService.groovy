package de.dewarim.goblin.camel

//import org.apache.camel.ProducerTemplate

/**
 * Class is currently deactivated as I have decided to not use Camel at the moment.
 */
class MessageSenderService {

    def camelContext

    void sendLearningMessage(msg){
        log.debug("sending message: $msg")
//        ProducerTemplate pt = camelContext.createProducerTemplate()
//        pt.sendBody("activemq:learning", msg)
    }

}
