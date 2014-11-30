package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class TestController {
     
    def grailsApplication
    
    def userTest() {
        return [testModeEnabled: grailsApplication.config.testMode]
    }    
    
    def adminTest() {
        return [testModeEnabled: grailsApplication.config.testMode]
    }
}
