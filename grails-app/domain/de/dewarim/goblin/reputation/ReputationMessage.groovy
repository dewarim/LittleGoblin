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

}
