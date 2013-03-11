grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.war.file = "target/${appName}.war"
grails.project.dependency.resolution = {

    inherits("global") {
        excludes "xercesImpl", "xmlParserAPIs", "xml-apis", 'groovy'
    }
    log "warn"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        mavenRepo "http://download.java.net/maven/2/"
    }

    dependencies {
        runtime 'postgresql:postgresql:9.1-901.jdbc4', {
            export = false
        }
        compile 'javax.mail:mail:1.4.6'
        compile 'javax.activation:activation:1.1.1'
        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.5.2") {
            excludes "groovy"
        }
        compile 'org.codehaus.gpars:gpars:1.0.0'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0", {
            export = false
        }
    }

    plugins {
        runtime ":hibernate:$grailsVersion", {
            export = false
        }
        build ":tomcat:$grailsVersion", {
            export = false
        }
        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"
        compile ':spring-security-core:1.2.7.3'
        compile ":webxml:1.4.1"
        test(":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }
        compile ":fixtures:1.2"
    }
}
