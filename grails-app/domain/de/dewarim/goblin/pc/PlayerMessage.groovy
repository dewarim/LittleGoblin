package de.dewarim.goblin.pc

/**
 *
 */
class PlayerMessage {

    static belongsTo = [pc:PlayerCharacter]

    String pcMessage
    Boolean displayed = false
    Date datum = new Date()

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof PlayerMessage)) return false

        PlayerMessage that = (PlayerMessage) o

        if (datum != that.datum) return false
        if (displayed != that.displayed) return false
        if (pc != that.pc) return false
        if (pcMessage != that.pcMessage) return false

        return true
    }

    int hashCode() {
        int result
        result = (pcMessage != null ? pcMessage.hashCode() : 0)
        result = 31 * result + (displayed != null ? displayed.hashCode() : 0)
        result = 31 * result + (datum != null ? datum.hashCode() : 0)
        result = 31 * result + (pc != null ? pc.hashCode() : 0)
        return result
    }
}
