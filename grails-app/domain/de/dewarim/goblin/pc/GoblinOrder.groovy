package de.dewarim.goblin.pc

import de.dewarim.goblin.social.ChatterBox

/**
 *
 */
class GoblinOrder {

    static hasMany = [members:PlayerCharacter, applications:OrderApplication, chatterBoxes:ChatterBox]
    static constraints = {
        name blank: false, unique: true
        description blank:true
        leader nullable:false
    }

    Long score = 0
    String name
    String description
    PlayerCharacter leader
    Long coins = 0

    ChatterBox fetchDefaultChatterBox(){
        return chatterBoxes.find{true} // return the first element.
    }
}
