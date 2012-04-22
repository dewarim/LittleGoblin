package de.dewarim.goblin.combat

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.FighterState

class MeleeFighter {

    static belongsTo = [melee:Melee]

    static constraints = {
        action nullable: true
        pc nullable: true
        // pc only needs to be nullable during initialization
        // (when Hibernate loads this class and tries to instantiate it) and testing.
    }

    MeleeFighter(){}

    MeleeFighter(Melee melee, PlayerCharacter pc){
        this.melee = melee
        this.pc = pc
        this.melee.fighters.add(this)
        this.pc.currentMelee = melee
    }

    MeleeAction action

    Integer place = 0
    Integer round = 1

    FighterState state = FighterState.ACTIVE
    PlayerCharacter pc
}
