package de.dewarim.goblin.combat

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.MeleeStatus;

class Melee{

    static hasMany = [fighters:MeleeFighter]

    static constraints = {
        winner nullable: true
        startTime( nullable: true)
    }

    MeleeStatus status = MeleeStatus.WAITING
    Date startTime
    PlayerCharacter winner
    Integer round = 1

}