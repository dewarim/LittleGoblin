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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof MeleeAction)) return false

        MeleeAction that = (MeleeAction) o

        if (actor != that.actor) return false
        if (feature != that.feature) return false
        if (featureConfig != that.featureConfig) return false
        if (initiative != that.initiative) return false
        if (item != that.item) return false
        if (melee != that.melee) return false
        if (target != that.target) return false
        if (type != that.type) return false

        return true
    }

    int hashCode() {
        int result
        result = (type != null ? type.hashCode() : 0)
        result = 31 * result + (melee != null ? melee.hashCode() : 0)
        result = 31 * result + (actor != null ? actor.hashCode() : 0)
        result = 31 * result + (target != null ? target.hashCode() : 0)
        result = 31 * result + (item != null ? item.hashCode() : 0)
        result = 31 * result + (feature != null ? feature.hashCode() : 0)
        result = 31 * result + (featureConfig != null ? featureConfig.hashCode() : 0)
        result = 31 * result + (initiative != null ? initiative.hashCode() : 0)
        return result
    }
}
