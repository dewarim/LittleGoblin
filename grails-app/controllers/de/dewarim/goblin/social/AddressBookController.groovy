package de.dewarim.goblin.social

import de.dewarim.goblin.BaseController
import grails.plugins.springsecurity.Secured

/**
 *
 */
class AddressBookController extends BaseController {

    @Secured(['ROLE_USER'])
    def list = {

    }

    @Secured(['ROLE_USER'])
    def removeRecipient = {

    }

    @Secured(['ROLE_USER'])
    def addRecipient = {

    }
}
