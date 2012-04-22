package de.dewarim.goblin.quest.script

import de.dewarim.goblin.IEncounterScript
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Quest
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType

/**
 *
 */
class PickupItem implements IEncounterScript {

    void execute(PlayerCharacter pc, Quest quest, String params) {
        def nodes = new XmlSlurper().parseText(params)
        def itemNames = nodes.items.item.list()
        for (name in itemNames) {            
            ItemType itemType = ItemType.findByName(name.text())
            if (itemType) {
                Item item = new Item(itemType, pc)
                item.save()
            }           
        }
    }
}
