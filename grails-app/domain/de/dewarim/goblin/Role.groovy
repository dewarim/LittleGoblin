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
}
