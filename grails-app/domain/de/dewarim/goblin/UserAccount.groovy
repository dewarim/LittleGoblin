package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter

/**
 * User domain class.
 */
class UserAccount {
    static transients = ['pass']
    static hasMany = [characters: PlayerCharacter]
    static mappedBy = [characters: 'user']

    def springSecurityService

    PlayerCharacter currentChar

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

    /** plain password to create a hashed password */
    String pass = '[secret]'

    Integer coins = 0
    Boolean premiumMember = false

    static constraints = {
        username(blank: false, unique: true)
        userRealName(blank: false)
        passwd(blank: false)
        enabled()
        email(nullable: true)
        currentChar(nullable: true)
        lastPasswordReset(nullable: true)
        salt(nullable: true)
    }

    Boolean checkRole(String roleName) {
        return userRoles.any { it.role.name.equals(roleName) }
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

    String getSalt() {
        return null
    }

    protected void encodePassword() {
        passwd = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(passwd) : passwd
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof UserAccount)) return false

        UserAccount that = o

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
        if (userRealName != that.userRealName) return false
        if (username != that.username) return false
        if (wantsResetPassword != that.wantsResetPassword) return false

        return true
    }

    int hashCode() {
        int result = mailConfirmationToken != null ? mailConfirmationToken.hashCode() : 0
        result = 31 * result + (username != null ? username.hashCode() : 0)
        return result
    }

    List<UserRole> getUserRoles() {
        return UserRole.findAllByUser(this)
    }
}
