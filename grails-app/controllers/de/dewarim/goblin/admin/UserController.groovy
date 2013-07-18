package de.dewarim.goblin.admin

import de.dewarim.goblin.Role
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.UserRole
import de.dewarim.goblin.pc.PlayerCharacter
import grails.plugins.springsecurity.Secured

/**
 *
 * will provide: start page, login, logout, account management
 *
 */
@Secured(['ROLE_ADMIN'])
class UserController {

	def springSecurityService

	// the delete, save and update actions only accept POST requests
	static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def index() {
		redirect action: 'list', params: params
	}

	def list() {
		if (!params.max) {
			params.max = 10
		}
        log.debug("users: ${UserAccount.list()}")
		[users: UserAccount.list(params)]
	}

	def show(Long id) {
		def user = UserAccount.get(id)
		if (!user) {
			flash.message = "User not found with id $id"
			redirect action: 'list'
			return
		}
		List roleNames = []
		for (role in user.authorities) {
			roleNames << role.name
		}
		roleNames.sort { n1, n2 ->
			n1 <=> n2
		}
		[user: user, roleNames: roleNames]
	}

	/**
	 * user delete action. Before removing an existing user,
	 * he should be removed from those authorities which he is involved.
	 */
	def delete(Long id) {

		UserAccount user = UserAccount.get(id)
		if (user) {
			def authPrincipal = springSecurityService.principal
			//avoid self-delete if the logged-in user is an admin
			if (!(authPrincipal instanceof String) && authPrincipal.username == user.username) {
				flash.message = "You can not delete yourself, please login as another admin and try again"
			}
			else {
//				UserRole.findAllByUser(user).each {ur -> 
//                    user.removeFromUserRoles(ur)
//                    ur.role.removeFromUserRoles(ur)
//                    ur.delete()
//                }
//				user.delete()
                // currently, users are not deleted but simply disabled,
                // as a user account may have a lot of objects connected to it,
                // for example items the user has created and given to other users.
                user.enabled = false
				flash.message = "User $params.id deleted."
			}
		}
		else {
			flash.message = "User not found with id $params.id"
		}

		redirect action: 'list'
	}

	def edit(Long id) {

		def user = UserAccount.get(id)
		if (!user) {
			flash.message = "User not found with id $id"
			redirect action: 'list'
		}
        else{
		    return [user:user]
        }
	}

	/**
	 * user update action.
	 */
	def update(Long id) {

		def user = UserAccount.get(id)
		if (!user) {
			flash.message = "User not found with id $id"
			redirect action: 'edit', id: id
			return
		}

		long version = params.version.toLong()
		if (user.version > version) {
			user.errors.rejectValue 'version', "user.optimistic.locking.failure",
				"Another user has updated this User while you were editing."
				render view: 'edit', model: [user:user]
			return
		}
        
        bindData(user, params, [include:['username', 'enabled', 'userRealName', 'description', 'email', 'emailShow']])
        
		if (params.passwd) {
			user.passwd = params.passwd
		}
		if (user.save()) {
            def selectedRoles = params.list("roles").collect {Role.get(it)}
            log.debug("selectedRoles: ${selectedRoles.dump()}")
            log.debug("userRoles: ${user.userRoles.dump()}")
            Role.list().each {Role role ->
                if (!selectedRoles.contains(role)) {
                    def uar = UserRole.findWhere(role: role, user: user)
                    if (uar != null) {
                        log.debug("delete role: $uar from user $user")
                        uar.user.removeFromUserRoles(uar)
                        uar.delete(flush: true)                        
                        if (springSecurityService.loggedIn &&
                                springSecurityService.principal.username == user.username) {
                            springSecurityService.reauthenticate user.username
                        }
                    }
                }
            }
            selectedRoles.each {role ->
                def uar = UserRole.findOrSaveWhere([role: role, user: user])
            }
			redirect action: 'show', id: user.id
		}
		else {
			render view: 'edit', model: [user:user]
		}
	}

	def create() {
		[user: new UserAccount(params), authorityList: Role.list()]
	}

	/**
	 * user save action.
	 */
	def save() {
		def user = new UserAccount()
		user.properties = params
		if (user.save()) {
            def selectedRoles = params.list("roles").collect {Role.get(it)}
            selectedRoles.each{ role ->
                def ur = new UserRole(user, role)
                ur.save()
            }            
			redirect action: 'show', id: user.id
		}
		else {
			render view: 'create', model: [authorityList: Role.list(), user: user]
		}
	}


}
