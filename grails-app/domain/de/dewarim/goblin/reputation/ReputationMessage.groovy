package de.dewarim.goblin.reputation

/**
 * A ReputationMessage describes a player character's current reputation status
 * with a faction in a short text, for example: "The dwarves of Deep Mountain would buy you a beer anytime.".
 *
 */
class ReputationMessage {

    /*
     */
    static belongsTo = [repMessageMap: ReputationMessageMap]

    /**
     * The message id for this reputation message. It is used by the I18n module to render a
     * localized version of the corresponding text.
     */
    String messageId
    Integer reputation

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ReputationMessage)) return false

        ReputationMessage that = o

        if (messageId != that.messageId) return false
        if (repMessageMap != that.repMessageMap) return false
        if (reputation != that.reputation) return false

        return true
    }

    int hashCode() {
        int result
        result = (messageId != null ? messageId.hashCode() : 0)
        result = 31 * result + (reputation != null ? reputation.hashCode() : 0)
        result = 31 * result + (repMessageMap != null ? repMessageMap.hashCode() : 0)
        return result
    }
}
