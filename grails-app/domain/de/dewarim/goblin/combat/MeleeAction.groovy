package de.dewarim.goblin.combat

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.MeleeActionType
import de.dewarim.goblin.Feature

/**
 * A MeleeAction is an action by a player during a Grand Melee fight.
 * It defaults to a normal attack against a random target, but may
 * also be the use of an item feature for the player's own benefit or the
 * detriment of an adversary.
 */
class MeleeAction {

    static constraints = {
        target nullable: true
        item nullable: true
        feature nullable: true
        featureConfig size: 1..65536 // somewhat arbitrary, but should be larger than 2^16 -1
        // (otherwise, Hibernate with MySQL may assign a broken varchar column instead of a text field of some kind)
    }

    MeleeActionType type = MeleeActionType.FIGHT
    Melee melee
    PlayerCharacter actor
    PlayerCharacter target

    /**
     * In case the player uses an item as his melee action, it's stored here.
     */
    Item item
    /**
     * The feature of the item to be used. (An item may have more than one feature)
     */
    Feature feature
    String featureConfig = "<config />"
    // note: I did not use a reference to ItemTypeFeature here as that class has a different use case
    // still, it is kind of redundant to recreate it here.

    Integer initiative = 0
}
