package de.dewarim.goblin.pc.crafting

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.pc.skill.QueueElement
import de.dewarim.goblin.item.Item

/**
 *
 */
class ProductionJob extends QueueElement {

    static belongsTo = [pc:PlayerCharacter, product:Product]
    static hasMany = [resources:ProductionResource]

    Integer amount = 1

    /**
     * Time to live - the PJ is examined several times until all components are
     * available or until the ttl is down to 0. With each check, the ttl is reduced
     * by 1 and the PJ is postponed by an hour (that is, the value for QueueElement.finished is
     * increased by 1 hour). 
     */
    Integer ttl = 3


    /**
     * Check if the ttl (time to live) of this ProductionJob is still positive. A PJ whose
     * ttl is 0 or less has to be deleted because the player seems to be unable to come up
     * with the required resources.
     * @return
     */
    Boolean alive(){
        return ttl > 0
    }

    /**
     * Add one hour to the "finished" time of this ProductionJob and reduce the ttl by one.
     * This method will be used if the player (for some reason) has not enough resources
     * to create the product.
     */
    void postpone(){
        finished = new Date(finished.time + 3600 * 1000)
    }



    /**
     * If a job contains the order to create multiple products  
     */
    void continueJob(){
        finished = new Date(finished.time + product.timeNeeded)
    }

}
