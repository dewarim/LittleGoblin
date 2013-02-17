package de.dewarim.goblin

/**
 * Render a help template using message ids and template names..
 * Alternatives would include:
 *  load help from database
 *  load help from external (template) file
 * Currently, using templates is easier and very flexible.
 */
class HelpController {

    def summonHelp(String messageId) {
        Help help = Help.findByMessageId(messageId)
        if(help){
            render(text:message(code:messageId))
            return
        }

        log.debug("Unidentified template: ${messageId?.encodeAsHTML()}")
        def tryTranslation = message(code:messageId.decodeURL())
        if(tryTranslation.equals(messageId)){
            render(text:message(code:'help.not.found'))
            return
        }

        render(text:tryTranslation)
    }
}
