package de.dewarim.goblin

/**
 * Basic class for help texts.
 * Expansion possibility: combine texts with templates and images for a comprehensive
 * help system which also may be used to compile larger bodies of documents automatically.
 * (so given a subject like "combat" the admin can add sub sections for weapons and armor which
 * then appear on the general help page).
 */
class Help {

    String messageId

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Help)) return false

        Help help = (Help) o

        if (messageId != help.messageId) return false

        return true
    }

    int hashCode() {
        return (messageId != null ? messageId.hashCode() : 0)
    }
}
