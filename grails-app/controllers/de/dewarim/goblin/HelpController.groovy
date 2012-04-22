package de.dewarim.goblin

/**
 * Render a help template using message ids and template names..
 * Alternatives would include:
 *  load help from database
 *  load help from external (template) file
 * Currently, using templates is easier and very flexible. 
 */
class HelpController {

    def summonHelp = {
        String messageId = params.messageId
        Help help = Help.findByMessageId(messageId)
        if(help){
            return render(text:message(code:messageId))
        }
        else{
            log.debug("Unidentified template: ${messageId?.encodeAsHTML()}")
            def tryTranslation = message(code:messageId.decodeURL())
            if(tryTranslation.equals(messageId)){
                return render(text:message(code:'help.not.found'))
            }
            else{
                return render(text:tryTranslation)
            }
        }
    }

}
