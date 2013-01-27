package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerMessage

class PlayerMessageService {

    def messageSource

    static transactional = true

    /**
     * Create a new PlayerMessage based upon the character's settings,
     * connect it with the specified player character, save it and return a reference.
     */
    PlayerMessage createMessage(pc, String messageId, List args) {
        if (args ==  null ){
            args = []
        }
//        String pmMessage = taglib.message(code:messageId,
//                args:args.collect{taglib.message(code:it.toString())}, locale:pc.user.locale)
        Locale locale = pc.user.locale
        List translatedArgs = args.collect{ messageSource.getMessage(it.toString(), null, it.toString(), locale )}
        String pmMessage = messageSource.getMessage(messageId, translatedArgs.toArray(), messageId, locale)
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

}
