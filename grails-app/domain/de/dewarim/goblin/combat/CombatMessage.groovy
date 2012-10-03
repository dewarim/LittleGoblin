package de.dewarim.goblin.combat

class CombatMessage {

    static belongsTo = [combat:Combat]
    static hasMany = [args:CombatMessageArg]
	String msg

    CombatMessage(){}

    CombatMessage(msg, msgParams){
        this.msg = msg
        def rankCounter = 0
        msgParams.each {mess ->
            log.debug("msg: $msg param: $mess")
            CombatMessageArg a = new CombatMessageArg(cma: mess, rank:rankCounter++, combatMessage: this)
            addToArgs(a)
        }
    }

    List fetchArgs(){
         // def l =  args.sort{a,b -> a.id <=> b.id }.collect{it.cma}
        // sorting by id failed for mysterious reasons.
        def l =  args.sort{a,b -> a.rank <=> b.rank }.collect{it.cma}
        return l
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof CombatMessage)) return false

        CombatMessage that = (CombatMessage) o

        if (combat != that.combat) return false
        if (msg != that.msg) return false

        return true
    }

    int hashCode() {
        int result
        result = (msg != null ? msg.hashCode() : 0)
        result = 31 * result + (combat != null ? combat.hashCode() : 0)
        return result
    }
}