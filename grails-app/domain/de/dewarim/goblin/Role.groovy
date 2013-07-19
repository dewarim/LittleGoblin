package de.dewarim.goblin

/**
 * Authority domain class.
 */
class Role {

	static hasMany = [userRoles: UserRole]

	/** description */
	String description
	/** ROLE String */
	String name

	static constraints = {
		name(blank: false, unique: true)
	}

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof Role)) return false

        Role role = o

        if (description != role.description) return false
        if (name != role.name) return false

        return true
    }

    int hashCode() {
        return name != null ? name.hashCode() : 0
    }
}
