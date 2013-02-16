package de.dewarim.goblin.camel

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class LearningDelayer {

    Logger log = LoggerFactory.getLogger(this.class)

    long computeDelay(String body){
        log.debug("received: $body")
        Node xml = new XmlParser().parseText(body)
        String delay = xml.delay?.text()?.trim()
        log.debug("delay: $delay")
        if(delay){
            return Long.parseLong(System.currentTimeMillis() + delay)
        }
        return 0
    }
}
