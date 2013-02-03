package de.dewarim.goblin.ticks

import de.dewarim.goblin.GlobalConfigService
import grails.plugin.spock.UnitSpec
import grails.spring.BeanBuilder
import grails.test.mixin.*
import groovy.mock.interceptor.MockFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.springframework.context.ApplicationContext

/**
 *
 */
@TestFor(Tick)
class TickSpec extends UnitSpec {

    Tick tick

    void setupSpec() {
        defineBeans {
            listenerTestBean(TickTestBean)
            globalConfigService(GlobalConfigService)
        }
    }

    def "create valid Tick"() {
        setup:
        mockForConstraintsTests(Tick)
        def mainContext = Mock(ApplicationContext)
        mainContext.getBean(beanName) >> applicationContext.getBean(beanName)

        when:
        tick = new Tick(currentTick: currentTick, name: name, active: active, beanName: beanName, tickLength: tickLength)
        tick.grailsApplication = [mainContext: mainContext]

        then:
        tick.validate()

        where:
        currentTick = 0
        name = 'The Tick'
        active = true
        beanName = 'listenerTestBean'
        tickLength = 1000
    }

    def "create invalid Tick with null values"() {
        setup:
        mockForConstraintsTests(Tick)
        def mainContext = Mock(ApplicationContext)
        mainContext.getBean(beanName) >> { applicationContext.getBean(beanName) }

        when:
        tick = new Tick(currentTick: currentTick, name: name, active: active, beanName: beanName, tickLength: tickLength)
        tick.grailsApplication = [mainContext: mainContext]
        tick.validate()

        then:
        tick.errors['beanName'] == 'nullable'
        tick.errors['active'] == 'nullable'
        tick.errors['tickLength'] == 'nullable'
        tick.errors['name'] == 'nullable'
        tick.errors['currentTick'] == 'nullable'

        where:
        currentTick = null
        name = null
        active = null
        beanName = null
        tickLength = null
    }

    def "create invalid Tick with broken values"() {
        setup:
        mockForConstraintsTests(Tick)
        def mainContext = Mock(ApplicationContext)
        mainContext.getBean(beanName) >> { applicationContext.getBean(beanName) }

        when:
        tick = new Tick(currentTick: 0, name: name, active: true, beanName: beanName, tickLength: tickLength)
        tick.grailsApplication = [mainContext: mainContext]
        tick.validate()

        then:
        tick.errors['beanName'] == 'bean.missing'
        tick.errors['tickLength'] == 'too.small'
        tick.errors['name'] == 'blank'

        where:
        tickLength = -1
        name = ''
        beanName = 'invalidBean'
    }

    def "create Tick with beanName for existing, but non-ITickListener-bean"() {
        setup:
        mockForConstraintsTests(Tick)
        def mainContext = Mock(ApplicationContext)
        mainContext.getBean(beanName) >> { applicationContext.getBean(beanName) }


        when:
        tick = new Tick(currentTick: 0, name: 'test', active: true, beanName: beanName, tickLength: 1000)
        tick.grailsApplication = [mainContext: mainContext]
        tick.validate()

        then:
        tick.errors['beanName'] == 'not.listener'

        where:
        beanName = 'globalConfigService'
    }
}
