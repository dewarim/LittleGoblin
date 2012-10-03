package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * User domain class.
 */
class UserAccount {
	static transients = ['pass']
	static hasMany = [userRoles: UserRole,characters:PlayerCharacter]

    def springSecurityService

	PlayerCharacter currentChar

	String salt = UUID.randomUUID().toString()

	/** Username */
	String username
	/** User Real Name*/
	String userRealName
	/** MD5 Password */
	String passwd
	/** enabled */
	Boolean enabled = false

    String mailConfirmationToken = UUID.randomUUID().toString()
    Boolean confirmationMailSent = false
    /**
     * If this flag is true, the user wants to reset his password.
     * A reset-password-mail will be send with a new token for
     */
    Boolean wantsResetPassword = false
    java.sql.Date lastPasswordReset

    Boolean accountExpired = false
    Boolean accountLocked = false
    Boolean passwordExpired = false

	String email
    Locale locale = new Locale('en')
	Boolean emailShow = false

	/** description */
	String description = ''

	/** plain password to create a MD5 password */
	String pass = '[secret]'

    Integer coins = 0
    Boolean premiumMember = false
    
	static constraints = {
		username(blank: false, unique: true)
		userRealName(blank: false)
		passwd(blank: false)
		enabled()
		email(nullable:true)
		currentChar(nullable:true)
        coins nullable:false
        lastPasswordReset(nullable: true)
	}

    Boolean checkRole(String roleName){
        return userRoles.any{it.role.name.equals(roleName)}
    }

    Set<Role> getAuthorities() {
       UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        passwd = springSecurityService.encodePassword(passwd, username)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof UserAccount)) return false

        UserAccount that = (UserAccount) o

        if (accountExpired != that.accountExpired) return false
        if (accountLocked != that.accountLocked) return false
        if (coins != that.coins) return false
        if (confirmationMailSent != that.confirmationMailSent) return false
        if (currentChar != that.currentChar) return false
        if (description != that.description) return false
        if (email != that.email) return false
        if (emailShow != that.emailShow) return false
        if (enabled != that.enabled) return false
        if (lastPasswordReset != that.lastPasswordReset) return false
        if (locale != that.locale) return false
        if (mailConfirmationToken != that.mailConfirmationToken) return false
        if (pass != that.pass) return false
        if (passwd != that.passwd) return false
        if (passwordExpired != that.passwordExpired) return false
        if (premiumMember != that.premiumMember) return false
        if (salt != that.salt) return false
        if (userRealName != that.userRealName) return false
        if (username != that.username) return false
        if (wantsResetPassword != that.wantsResetPassword) return false

        return true
    }

    int hashCode() {
        int result
        result = (currentChar != null ? currentChar.hashCode() : 0)
        result = 31 * result + (salt != null ? salt.hashCode() : 0)
        result = 31 * result + (username != null ? username.hashCode() : 0)
        result = 31 * result + (userRealName != null ? userRealName.hashCode() : 0)
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0)
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0)
        result = 31 * result + (mailConfirmationToken != null ? mailConfirmationToken.hashCode() : 0)
        result = 31 * result + (confirmationMailSent != null ? confirmationMailSent.hashCode() : 0)
        result = 31 * result + (wantsResetPassword != null ? wantsResetPassword.hashCode() : 0)
        result = 31 * result + (lastPasswordReset != null ? lastPasswordReset.hashCode() : 0)
        result = 31 * result + (accountExpired != null ? accountExpired.hashCode() : 0)
        result = 31 * result + (accountLocked != null ? accountLocked.hashCode() : 0)
        result = 31 * result + (passwordExpired != null ? passwordExpired.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (locale != null ? locale.hashCode() : 0)
        result = 31 * result + (emailShow != null ? emailShow.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (pass != null ? pass.hashCode() : 0)
        result = 31 * result + (coins != null ? coins.hashCode() : 0)
        result = 31 * result + (premiumMember != null ? premiumMember.hashCode() : 0)
        return result
    }
}
