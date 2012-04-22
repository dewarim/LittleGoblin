package de.dewarim.goblin.camel

/**
 *  Receives activeMQ messages whenever a player character learns a new skill.
 * # currently deactivated. #
 *
 */
class LearningListenerService {

    def eventListener(eventMessage){
        log.debug("received event: ${eventMessage}")
        log.debug("which is of instance: ${eventMessage.class}")
    }

}
