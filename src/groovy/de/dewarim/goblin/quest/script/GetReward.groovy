package de.dewarim.goblin.quest.script

import de.dewarim.goblin.IEncounterScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Quest
import de.dewarim.goblin.pc.PlayerMessage
import de.dewarim.goblin.reputation.Reputation
import de.dewarim.goblin.reputation.Faction
import org.apache.log4j.Logger
import de.dewarim.goblin.PlayerMessageService

import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA

/**
 *
 */
class GetReward implements IEncounterScript{

    def log = Logger.getLogger(this.class)
    def ctx = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
    def playerMessageService = ctx.playerMessageService

    void execute(PlayerCharacter pc, Quest quest, String params) {
        log.debug("Player ${pc.name} gets a reward.")
        def nodes = new XmlSlurper().parseText(params)
        if(nodes.gold?.text()){
            Integer gold = Integer.parseInt(nodes.gold.text())
            pc.gold = pc.gold+gold
            playerMessageService.createMessage(pc, 'reward.gold', [gold])
        }
        if(nodes.xp?.text()){
            Integer xp = Integer.parseInt(nodes.xp.text())
            pc.xp = pc.xp+xp
            playerMessageService.createMessage(pc, 'reward.xp', [xp])
        }
        if(nodes.reputation?.faction){
            try {
                def factions = nodes.reputation.faction
                factions.each {factionNode ->
                    def name = factionNode.name?.text()
                    Integer level = Integer.parseInt(factionNode.level?.text()?.trim())
                    Faction faction = Faction.findByName(name)
                    if(! faction){
                        throw new RuntimeException("Faction $name was not found in the database!")
                    }
                    Reputation rep = pc.reputations?.find{it.faction.equals(faction)}
                    if(rep){
                        rep.level = rep.level + level
                    }
                    else{
                        rep = new Reputation(level: (faction.startLevel + level), pc:pc, faction:faction)
                        rep.save()
                        pc.addToReputations(rep)
                        faction.addToPlayerReputations(rep)
                    }
                    playerMessageService.createMessage(pc, 'reward.reputation', [faction.name, level])
                }
            }
            catch (Exception ex){
                log.warn('Failed to compute reputation reward:',ex)
            }
        }
        quest.successful = true
        // TODO: reward with items.
    }
}
