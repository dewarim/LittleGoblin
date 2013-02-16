package de.dewarim.goblin

/**
 * There are some objects that may reference Groovy or Java script classes, for example
 * instances of class "Encounter". To avoid having the admin enter the direct name of the
 * script class every time he wants to create a new Encounter, this class is used.<br>
 * GoblinScripts should probably only be created by the programmers.
 */
class GoblinScript {

    String name
    Class clazz

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof GoblinScript)) return false

        GoblinScript that = o

        if (clazz != that.clazz) return false
        if (name != that.name) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0)
        return result
    }
}
