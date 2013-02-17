package de.dewarim.goblin

class UserProperty {
	String name
	String upValue

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof UserProperty)) return false

        UserProperty that = o

        if (name != that.name) return false
        if (upValue != that.upValue) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (upValue != null ? upValue.hashCode() : 0)
        return result
    }
}
