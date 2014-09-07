import de.dewarim.goblin.ticks.TickTestBean
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import org.springframework.security.authentication.dao.DaoAuthenticationProvider

//import org.apache.camel.spring.CamelContextFactoryBean
//import org.apache.camel.spring.CamelProducerTemplateFactoryBean
//import org.apache.camel.spring.CamelConsumerTemplateFactoryBean
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.springframework.jms.connection.CachingConnectionFactory
//import de.dewarim.goblin.camel.LearningDelayer
//import org.apache.camel.component.quartz.QuartzComponent

// Place your Spring DSL code here
beans = {

    // does not work because of circular dependencies.
//    getReward(GetReward){reward ->
//        reward.autowire = true
//    }

//    goblinRoute(de.dewarim.goblin.routes.GoblinRouteBuilder)
//
//    camelContext(CamelContextFactoryBean) {
//        id = 'camel1'
//    }

//
    // broker service
//    def brokerName ='goblinBroker'
//    brokerService(org.apache.activemq.broker.BrokerService){
//        brokerName= "${brokerName}"
//        addConnector('tcp://localhost:61616')
//    }
//
//    quartz(org.apache.camel.component.quartz.QuartzComponent){
//        startDelaySeconds = 10
//    }

//    learningDelayer(de.dewarim.goblin.camel.LearningDelayer)
    
    saltSource(NullSaltSource)
    listenerTestBean(TickTestBean)
}
