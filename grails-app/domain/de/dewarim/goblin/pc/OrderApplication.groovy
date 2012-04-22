package de.dewarim.goblin.pc

class OrderApplication {

    static belongsTo = [order:GoblinOrder]
    static constraints = {
    }

    PlayerCharacter applicant
    String applicationMessage
    
}
