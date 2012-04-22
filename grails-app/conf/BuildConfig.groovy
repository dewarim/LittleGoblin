grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.target.level = 1.7
grails.project.source.level = 1.6
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsRepo "http://grails.org/plugins"
        grailsCentral()
        grailsPlugins()
        grailsHome()

        // snapshots-repos needed or webtest won't run:
//         mavenRepo 'http://webtest.canoo.com/webtest/
//        mavenRepo 'http://htmlunit.sourceforge.net/m2-repo-snapshots'

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
//        mavenLocal()
        mavenCentral()
//        mavenRepo "http://snapshots.repository.codehaus.org"
//        mavenRepo "http://repository.codehaus.org"
//        mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.5'
        runtime "org.grails.plugins:hibernate:$grailsVersion"
        runtime "org.grails.plugins:jquery:1.7.1"
        runtime "org.grails.plugins:resources:1.1.6"
        compile "org.grails.plugins:spring-security-core:1.2.7.3"
        compile "org.grails.plugins:mail:1.0"
        runtime 'postgresql:postgresql:9.1-901.jdbc4'
        build "org.grails.plugins:tomcat:$grailsVersion"

    }

}