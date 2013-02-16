package de.dewarim.goblin.quest.script

import de.dewarim.goblin.IEncounterScript
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Quest

/**
 *
 */
class PickupItem implements IEncounterScript {

    void execute(PlayerCharacter pc, Quest quest, String params) {
        def nodes = new XmlSlurper().parseText(params)
        for (name in nodes.items.item.list()) {
            ItemType itemType = ItemType.findByName(name.text())
            if (itemType) {
                new Item(itemType, pc).save()
            }
        }
    }
}
