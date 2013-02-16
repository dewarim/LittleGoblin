package de.dewarim.goblin.quest

/**
 *
 */
class QuestGiver {

    static hasMany = [templates:QuestTemplate]
    String name
    String description

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof QuestGiver)) return false

        QuestGiver that = o

        if (description != that.description) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        return result
    }
}
