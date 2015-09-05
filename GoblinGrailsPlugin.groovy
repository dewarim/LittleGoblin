class GoblinGrailsPlugin {

    def version = "0.5.2.2"
    def grailsVersion = "2.4 > *"
    def pluginExcludes = [
            "docs/**",
            "grails-app/views/error.gsp",
            "src/docs/**",
            "src/templates/**",
            "web-app/images/**"
    ]

    def title = "Little Goblin Plugin"
    def description = "Little Goblin is a browser game framework written in Grails."

    def documentation = "http://littlegoblin.de"
    def license = "APACHE"
    def developers = [
            [name: "Ingo Wiarda", email: "ingo_wiarda@dewarim.de"]
    ]

    def issueManagement = [system: "Github", url: "https://github.com/dewarim/LittleGoblin/issues"]
    def scm = [url: "https://github.com/dewarim/LittleGoblin"]
}
