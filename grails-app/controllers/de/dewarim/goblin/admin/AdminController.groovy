package de.dewarim.goblin.admin

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AdminController {

    def index () { }
}
