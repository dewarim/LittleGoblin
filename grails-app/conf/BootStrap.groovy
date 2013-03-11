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
        
        fixtureLoader.load('game/equipmentSlotTypes')
        fixtureLoader.load('game/scripts')
        fixtureLoader.load('game/towns')
        fixtureLoader.load('game/images')
        
        PlayerCharacter gob = initPlayer(UserAccount.findByUsername('anon'))

        fixtureLoader.load('game/combatAttributeTypes')
       
        initFactions()
        initOrders(gob)

        fixtureLoader.load('game/configEntries')
        // initializes: towns, guilds, dice, skilklSets 
        fixtureLoader.load('game/academies')
        initHelp()

        fixtureLoader.load('game/shops')
        fixtureLoader.load('game/items')
        fixtureLoader.load('game/productComponents')
        fixtureLoader.load('game/mobs')
        fixtureLoader.load('game/questTemplates')
        
        initItems(gob)
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

    PlayerCharacter initPlayer(user) {
        log.debug("initialize player character")
        PlayerCharacter gob = new PlayerCharacter(name: 'Gobli', user: user, gold: 100, xp: 10)
        gob.save()
        gob.initializePlayerCharacter()

        def boxes = MailBoxType.list()
        boxes.each {boxType ->
            MailBox mailBox = new MailBox(owner: gob, boxType: boxType)
            mailBox.save()
        }

        return gob
    }


    void initItems(PlayerCharacter littleGoblin) {
        def ore = ItemType.findByName('ore')
        def iron = ItemType.findByName('iron')
        def someOre = new Item(type: ore, amount: 10, owner: littleGoblin)
        def someBars = new Item(type: iron, amount: 10, owner: littleGoblin)
        someOre.save()
        someBars.save()

    }

    void initFactions() {
        log.debug("initialize Factions")

        def repMessages = ['unknown': [0], 'good': [1], 'very.good': [11],
                'very.very.good': [21],
                'best': [41],
                'bad': [-1], 'very.bad': [-11],
                'extremely.bad': [-21],
                'worst': [-41]]

        ReputationMessageMap rmmDwarfs = new ReputationMessageMap(name: 'rmm.dwarves')
        ReputationMessageMap rmmElves = new ReputationMessageMap(name: 'rmm.elves')
        rmmElves.save()
        rmmDwarfs.save()


        repMessages.each {messageId, repRange ->
            ReputationMessage rmd = new ReputationMessage(messageId: "reputation.$messageId", reputation: repRange[0],
                    repMessageMap: rmmDwarfs)
            rmmDwarfs.addToRepMessages(rmd)
            rmd.save()
            ReputationMessage rme = new ReputationMessage(messageId: "reputation.$messageId", reputation: repRange[0],
                    repMessageMap: rmmElves)
            rmmElves.addToRepMessages(rme)
            rme.save()
        }

        Faction elves = new Faction(name: 'faction.elves', description: 'faction.elves.description', repMessageMap: rmmElves)
        elves.save()
        rmmElves.faction = elves

        Faction dwarfs = new Faction(name: 'faction.dwarves', description: 'faction.dwarves.description', repMessageMap: rmmDwarfs)
        dwarfs.save()
        rmmDwarfs.faction = dwarfs
    }

    void initOrders(PlayerCharacter pc) {
        log.debug("initialize Orders")

        GoblinOrder order = new GoblinOrder(name: 'Order of the Ebon Hand', leader: pc, description: 'The legendary Order of the Ebon Hand')
        order.addToMembers(pc)
        order.save()
        pc.goblinOrder = order

    }

    void initHelp() {
        Help academyHelp = new Help(messageId: 'help.skillSets')
        academyHelp.save()
    }
}
