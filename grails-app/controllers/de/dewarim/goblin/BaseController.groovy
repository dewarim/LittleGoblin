package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.item.Item

/**
 * Base class for Controller classes.
 */

class BaseController {

    def session
    def springSecurityService
    def inputValidationService

    /**
     * Fetch the player character specified by the "pc" parameter.
     * Make sure that it is a pc belonging to the logged in user to prevent impersonation attacks.
     */
    protected PlayerCharacter fetchPc(session) {
        def pc = null
        if (params.pc) {
            pc = PlayerCharacter.get(params.pc)
        }
        else if (session?.pc) {
            pc = PlayerCharacter.get(session.pc)
        }

        if (!pc) {
            log.debug("no pc found.")
            return null
        }

        def user = fetchUser()
        if (!pc?.user?.equals(user)) {
            return null
        }
        return pc
    }

    protected UserAccount fetchUser() {
        return (UserAccount) springSecurityService.currentUser
    }

    /**
     * Render an exception by returning a 503 HTTP status code and the localized exception message
     * as text content.
     * @param e the exception you want to render.
     */
    protected void renderException(Exception e) {
        render(status: 503, text: message(code: e.getLocalizedMessage()))
    }

    protected Item fetchItem(PlayerCharacter pc) {
        if (!pc) {
            throw new RuntimeException('error.no.pc')
        }
        def item = (Item) inputValidationService.checkObject(Item.class, params.item, true)
        if (!item) {
            throw new RuntimeException('error.item_not_found')
        }
        if (!pc == item.owner) {
            throw new RuntimeException('error.wrong_owner')
        }
        return item
    }

}
