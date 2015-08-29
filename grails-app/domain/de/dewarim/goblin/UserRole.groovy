package de.dewarim.goblin

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Mapping class between User and Role.
 */
class UserRole{

	UserAccount user
	Role role

    UserRole(){

    }

    UserRole(UserAccount user, Role role){
        this.user = user
        this.role = role
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