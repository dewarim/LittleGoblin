package de.dewarim.goblin.social

/**
 *
 */
class MailBoxType {

    static hasMany = [boxes:MailBox]

    String name

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof MailBoxType)) return false

        MailBoxType that = o

        if (name != that.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}
