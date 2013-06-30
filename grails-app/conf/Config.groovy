// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

grails.config.locations = ["classpath:${appName}-config.groovy",
        "file:${userHome}/.grails/${appName}-config.groovy",
        "file:${System.env.LITTLE_GOBLIN_HOME}/${appName}-config.groovy"
]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// enable query caching by default
grails.hibernate.cache.queries = false
// automatically flush updates to database on save():
grails.gorm.autoFlush = true
// fail if user tries to save object with errors.
grails.gorm.failOnError = true

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://schedim.de/${appName}"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //


    appenders {
        //disable stacktrace file
        'null' name: 'stacktrace'
        console name: 'stdout', layout: pattern(conversionPattern: '%c %m%n')
    }

    error 'org.springframework.core.io.support.PathMatchingResourcePatternResolver',
            'org.codehaus.groovy.grails.plugins.DefaultGrailsPlugin',
            'org.codehaus.groovy.grails.commons',
            'org.codehaus.groovy.grails.validation',
            'org.grails.plugin.resource',
            'org.apache.catalina',
            'org.apache.coyote',
            'org.apache.tomcat',
            'org.codehaus.groovy.grails.web.pages.GroovyPageResourceLoader',
            'org.codehaus.groovy.grails',
            'org.apache.naming',
            'grails.app.taglib.org.grails.plugin.resource.ResourceTagLib',
            'org.apache.commons',
            'grails.plugin.fixtures.builder.processor.FixtureBeanPostProcessor'

    debug 'grails.app.controller',
            'grails.app.domain.de.dewarim.goblin',
            'grails.app.domain.de.dewarim.goblin.Creature',
            'grails.app.bootstrap',
            'de.dewarim.goblin.admin',
            'de.dewarim.goblin.admin.EquipmentSlotTypeAdminController'
            
//			'org.codehaus.groovy.grails.web.servlet',  //  controllers
//			'org.codehaus.groovy.grails.web.pages', //  GSP
//			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
//			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
//			'org.codehaus.groovy.grails.web.mapping', // URL mapping
//			'org.codehaus.groovy.grails.commons', // core / classloading
//			'org.codehaus.groovy.grails.plugins', // plugins
//			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
//			'org.springframework',
//			'org.hibernate'
//			'net.sf.ehcache.hibernate'
    root {
        debug 'stdout'
    }
}


grails.views.javascript.library = "jquery"
/*jquery {
    sources = 'jquery' // Holds the value where to store jQuery-js files /web-app/js/
    version = '1.6.2' // The jQuery version in use
}*/

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'de.dewarim.goblin.UserAccount'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'de.dewarim.goblin.UserRole'
grails.plugins.springsecurity.authority.className = 'de.dewarim.goblin.Role'
grails.plugins.springsecurity.userLookup.passwordPropertyName = 'passwd'
grails.plugins.springsecurity.authority.nameField = 'name'
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/portal/start'
grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = 'username'

grails.views.gsp.keepgenerateddir = '/tmp/gsp'

grails.doc.images=new File('src/docs/images')
