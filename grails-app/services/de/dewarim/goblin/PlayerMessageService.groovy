package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerMessage
import org.codehaus.groovy.grails.commons.ApplicationHolder

class PlayerMessageService {

    def messageSource

    static transactional = true

    def taglib = getApplicationTagLib()

    /**
     * Create a new PlayerMessage based upon the character's settings,
     * connect it with the specified player character, save it and return a reference.
     */
    PlayerMessage createMessage(pc, messageId, args) {
        if (args ==  null ){
            args = []
        }
        String pmMessage = taglib.message(code:messageId,
                args:args.collect{taglib.message(code:it.toString())}, locale:pc.user.locale)
        PlayerMessage pm = new PlayerMessage(pcMessage:pmMessage, pc:pc)
        pm.save()
        pc.addToPcMessages(pm)
        return pm
    }

    List fetchPlayerMessages(pc){
        List pms = PlayerMessage.findAll("from PlayerMessage as pm where pm.pc=? order by pm.id desc ", [pc], [max:5])
        log.debug("playermessages found: ${pms?.size()}")
        return pms
    }

    def getApplicationTagLib(){
        def ctx = ApplicationHolder.getApplication().getMainContext()
        return ctx.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
    }
}
