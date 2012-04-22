package de.dewarim.goblin.combat

/**
 *
 */
class CombatMessageArg {

    static belongsTo = [combatMessage:CombatMessage]
    String cma
    Integer rank = 0

    CombatMessageArg(){}
    CombatMessageArg(String cma){
        this.cma = cma
    }
}
