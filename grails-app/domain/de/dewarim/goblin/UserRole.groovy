package de.dewarim.goblin

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Mapping class between User and Role.
 */
class UserRole{

    static belongsTo = [user:UserAccount, role:Role]

    UserRole(){

    }

    UserRole(UserAccount user, Role role){
        this.user = user
        this.role = role
        user.addToUserRoles(this)
        role.addToUserRoles(this)
    }


	boolean equals(other) {
		if (!(other instanceof UserRole)) {
			return false
		}

		other.user?.id == user?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

}