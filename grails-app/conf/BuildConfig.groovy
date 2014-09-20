grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.war.file = "target/${appName}.war"
grails.project.dependency.resolver = "maven"
grails.reload.enabled = true
grails.project.dependency.resolution = {

    inherits("global") {
        excludes "xercesImpl", "xmlParserAPIs", "xml-apis", 'groovy'
    }
    log "info"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        runtime 'postgresql:postgresql:9.1-901.jdbc4', {
            export = false
        }
        compile 'javax.mail:mail:1.4.7'
        compile 'javax.activation:activation:1.1.1'
        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1") {
            excludes "groovy"
        }
        compile 'org.codehaus.gpars:gpars:1.2.1'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0", {
            export = false
        }
    }

    plugins {
        runtime ":hibernate:3.6.10.17", {
            export = false
        }
        build ":tomcat:7.0.54", {
            export = false
        }
        compile ':release:3.0.1', ':rest-client-builder:1.0.3',  {
            export = false
        }
        compile(':spring-security-core:2.0-RC4')
//        compile(":rest-client-builder:2.0.3") {
//            export = false
//        }
        compile ":webxml:1.4.1"
        test(":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }
        compile ":fixtures:1.3"
        compile ":asset-pipeline:1.9.9"
    }
}
