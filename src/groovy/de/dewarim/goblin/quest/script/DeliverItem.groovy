package de.dewarim.goblin.quest.script

import de.dewarim.goblin.IEncounterScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Quest

import de.dewarim.goblin.item.Item
import org.apache.log4j.Logger

/**
 *
 */
class DeliverItem implements IEncounterScript {

    def log = Logger.getLogger(this.class)

    void execute(PlayerCharacter pc, Quest quest, String params) {
        log.debug("${pc.name} delivers items.")
        def nodes = new XmlSlurper().parseText(params)
        def itemNames = nodes.items.item.list()
        Boolean complete = true
        itemNames.each{name ->
            if(! pc.items?.find{it.type.name.equals(name.text())}){
                complete = false
            }
        }
        if(complete){
            itemNames.each{name ->
                // TODO: add handling of countable items.
                Item item = pc.items?.find{it.type.name.equals(name.text())}
//                pc.removeFromItems(item)
                item.delete()

            }
            def nextStepName = nodes.steps.success.text()
            quest.continueQuest(nextStepName)
        }
        else{
            def nextStepName = nodes.steps.fail.text()
            quest.continueQuest(nextStepName) // throws RE if nSN is not found.
        }
    }


}
