import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.ticks.Tick
import de.dewarim.goblin.reputation.Faction
import de.dewarim.goblin.reputation.ReputationMessageMap
import de.dewarim.goblin.reputation.ReputationMessage
import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.social.MailBoxType
import de.dewarim.goblin.social.MailBox
import de.dewarim.goblin.social.ChatterBox
//import org.apache.camel.CamelContext
//import javax.jms.ConnectionFactory
//import org.apache.camel.component.jms.JmsComponent
//import org.apache.camel.ProducerTemplate
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.apache.activemq.camel.component.ActiveMQComponent


import grails.util.Environment
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.Help

import grails.plugin.fixtures.*

class BootStrap {
    def springSecurityService
    def grailsApplication
    def tickService
    def fixtureLoader

//    def camelContext
//    def brokerService

    def init = { servletContext ->
        // currently disabled
//        brokerService.start()
//        camelContext.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"))
//        ((CamelContext) camelContext).addRoutes (new GoblinRouteBuilder())
//        camelContext.start()
//        ProducerTemplate template =  camelContext.createProducerTemplate()
//        template.sendBody('activemq:learning', 'boo')

        if (!UserAccount.list().isEmpty()) {
            return
        }

        log.debug("LITTLE_GOBLIN_HOME: ${System.env.LITTLE_GOBLIN_HOME}")
        log.debug("Facebook.config: ${grailsApplication.config.facebook}")

        fixtureLoader.load('system/rolesAndUsers')
        fixtureLoader.load('system/mailBox')
        fixtureLoader.load('system/configEntries')
        fixtureLoader.load('system/licenses')
        
        // Note: the order of fixture files is in some cases relevant
        // as for example 'game/items' will generate objects used by 'game/playerCharacters' 
        fixtureLoader.load('game/equipmentSlotTypes')
        fixtureLoader.load('game/scripts')
        fixtureLoader.load('game/towns')
        fixtureLoader.load('game/images')
        fixtureLoader.load('game/combatAttributeTypes')
        fixtureLoader.load('game/reputationMessages')
        fixtureLoader.load('game/factions')
        fixtureLoader.load('game/configEntries')
        // initializes: towns, guilds, dice, skillSets 
        fixtureLoader.load('game/academies')
        fixtureLoader.load('game/shops')
        fixtureLoader.load('game/items')
        fixtureLoader.load('game/productComponents')
        fixtureLoader.load('game/mobs')
        fixtureLoader.load('game/questTemplates')
        fixtureLoader.load('game/playerCharacters')
        fixtureLoader.load('game/orders')
        
        PlayerCharacter.list().each{pc ->
            pc.initializePlayerCharacter()
        }
        
        initHelp()
        initTicks()
    }

    def destroy = {
        tickService.stopAll()
    }

    void initTicks(){
        log.debug("initialize ticks")
        def tickListeners = ['skillService', 'productionService', 'meleeService']
        if (Environment.current == Environment.TEST){
            tickListeners.add('listenerTestBean')
        }
        tickListeners.each{name ->
            def tick = new Tick(name: "tick.service.$name", beanName:name)
            tick.save()
        }
        tickService.initialize()
    }

    void initHelp() {
        Help academyHelp = new Help(messageId: 'help.skillSets')
        academyHelp.save()
    }
}
