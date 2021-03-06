grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.8
grails.project.source.level = 1.8
grails.project.war.file = "target/${appName}.war"
grails.project.dependency.resolver = "maven"
grails.reload.enabled = true
grails.project.dependency.resolution = {

    inherits("global") {
        excludes "xercesImpl", "xmlParserAPIs", "xml-apis", 'groovy'
    }
    log "verbose"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        runtime 'postgresql:postgresql:9.1-901.jdbc4', {
            export = false
        }
        compile("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1") {
            excludes "groovy"
        }
        compile 'org.codehaus.gpars:gpars:1.2.1'
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0", {
            export = false
        }
        test "org.grails:grails-datastore-test-support:1.0.1-grails-2.4"
        compile "com.google.guava:guava:18.0"
    }

    plugins {
//        runtime ":hibernate4:4.3.6.1",{
//            export = false
//        }
        runtime ":hibernate:3.6.10.19", {
            export = false
        }
        build ":tomcat:8.0.15", {
            export = false
        }
        compile ':release:3.1.1', ':rest-client-builder:2.1.1',  {
            export = false
        }
        compile(':spring-security-core:2.0-RC5')
//        compile(":rest-client-builder:2.0.3") {
//            export = false
//        }
        runtime ':jquery:1.11.1'
        compile ":webxml:1.4.1"
        test(":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }
        compile ":fixtures:1.3"
        compile ":asset-pipeline:2.3.9"
//        compile ":build-test-data:2.2.2",{
//            export = false
//        }
    }
}
