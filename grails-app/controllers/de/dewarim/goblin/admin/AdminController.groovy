package de.dewarim.goblin.admin

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class AdminController {

    def index () { }
    
}
