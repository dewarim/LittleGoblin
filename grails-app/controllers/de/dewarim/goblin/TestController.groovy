package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class TestController {
     
    def grailsApplication
    
    def index() {
        return [testModeEnabled: grailsApplication.config.testMode]
    }
}
