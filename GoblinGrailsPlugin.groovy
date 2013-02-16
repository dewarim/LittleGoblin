class GoblinGrailsPlugin {

    def version = "0.3.2"
    def grailsVersion = "2.0 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Little Goblin Plugin" 
    def author = "Ingo Wiarda"
    def authorEmail = "ingo_wiarda@dewarim.de"
    def description = '''\
Little Goblin is a browser game framework written in Grails.
'''

    def documentation = "http://littlegoblin.de"
    def license = "APACHE"
    def developers = [
            [ name: "Ingo Wiarda", email: "ingo_wiarda@dewarim.de" ]
    ]

    def issueManagement = [ system: "Github", url: "https://github.com/dewarim/LittleGoblin/issues" ]
    def scm = [ url: "https://github.com/dewarim/LittleGoblin" ]

    def doWithWebDescriptor = { xml ->
        
    }

    def doWithSpring = {
        
    }

    def doWithDynamicMethods = { ctx ->
        
    }

    def doWithApplicationContext = { applicationContext ->
    
    }

    def onChange = { event ->
        
    }

    def onConfigChange = { event ->
        
    }

    def onShutdown = { event ->
        
    }
}
