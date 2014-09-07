package de.dewarim.goblin.social

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.BaseController

/**
 *
 */
@Secured(['ROLE_USER'])
class AddressBookController extends BaseController {

    def list() {
    }

    def removeRecipient() {
    }

    def addRecipient() {
    }
}
