package de.dewarim.goblin.pc

class OrderApplication {

    static belongsTo = [order:GoblinOrder]
    static constraints = {
    }

    PlayerCharacter applicant
    String applicationMessage

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof OrderApplication)) return false

        OrderApplication that = o

        if (applicant != that.applicant) return false
        if (applicationMessage != that.applicationMessage) return false
        if (order != that.order) return false

        return true
    }

    int hashCode() {
        int result
        result = (applicant != null ? applicant.hashCode() : 0)
        result = 31 * result + (applicationMessage != null ? applicationMessage.hashCode() : 0)
        result = 31 * result + (order != null ? order.hashCode() : 0)
        return result
    }
}
