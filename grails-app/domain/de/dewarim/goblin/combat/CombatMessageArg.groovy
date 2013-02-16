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

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof CombatMessageArg)) return false

        CombatMessageArg that = o

        if (cma != that.cma) return false
        if (combatMessage != that.combatMessage) return false
        if (rank != that.rank) return false

        return true
    }

    int hashCode() {
        int result
        result = (cma != null ? cma.hashCode() : 0)
        result = 31 * result + (rank != null ? rank.hashCode() : 0)
        result = 31 * result + (combatMessage != null ? combatMessage.hashCode() : 0)
        return result
    }
}
