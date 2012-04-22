package de.dewarim.goblin.landing;
import de.dewarim.goblin.HighScore

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.UserRole
import de.dewarim.goblin.Role

class PortalController extends BaseController {

    def inputValidationService
    def globalConfigService
    def mailService

    /**
     * The landing page (start page of Little Goblin)
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def landing = {

        return [
                highscore: HighScore.list(max: 5, sort: 'xp', order: 'desc')
        ]
    }

    def index = {
        return redirect(action: landing)
    }

    def imprint = {

    }

    @Secured(['ROLE_USER'])
    def start = {
        def user = fetchUser()

        return [user: user,
                chars: user.characters?.sort {a, b -> a.name.toLowerCase() <=> b.name.toLowerCase()},
                createError: params.createError
        ]
    }

    def register = {
        def minNameLength = globalConfigService.fetchValueAsInt('username.min.length', 3)
        def minPassLength = globalConfigService.fetchValueAsInt('password.min.length', 6)
        return [nameOfTheGame: grailsApplication.config.gameName ?: 'Little Goblin',
                username: params.username?.encodeAsHTML(),
                minPassLength: minPassLength,
                minNameLength: minNameLength,
                email: params.username?.encodeAsHTML(),

        ]
    }

    def doRegister = {
        try {
            if (params.username?.trim()?.length() == 0) {
                throw new RuntimeException('registration.bad.name')
            }
            // check if username is free
            def username = inputValidationService.checkAndEncodeText(params, "username", "registration.username")
            def minNameLength = globalConfigService.fetchValueAsInt('username.min.length', 3)
            if (username.length() < minNameLength) {
                throw new RuntimeException(message(code: 'registration.short.username', args: [minNameLength]))
            }
            // check if passwords match
            def password = params.password
            def password2 = params.password2
            if (password != password2) {
                throw new RuntimeException('registration.password.mismatch')
            }
            def minPassLength = globalConfigService.fetchValueAsInt('password.min.length', 6)
            if (password.length() < minPassLength) {
                throw new RuntimeException(message(code: 'registration.short.password', args: [minPassLength]))
            }

            // check if email at least looks like it may be a valid address
            String email = params.email
            if (!email || !email.contains('@')) {
                throw new RuntimeException('registration.bad.email')
            }

            // check if we know this email
            UserAccount existingUser = UserAccount.findByEmail(email.toLowerCase())
            if(existingUser){
                log.debug("user with email $email already exists as ${existingUser.username}")
                throw new RuntimeException(message(code:'registration.used.email'))
            }

            // create user
            UserAccount newAccount = new UserAccount(username:username, email:email, userRealName: username)
            newAccount.passwd = password
            newAccount.save()
            Role role = Role.findByName('ROLE_USER')
            UserRole userRole = new UserRole(newAccount, role)
            userRole.save()

            // send registration mail
            def mailConfig = grailsApplication.config.registration
            def link = "http://${mailConfig.serverUrl}/portal/confirmRegistration?uuid=${newAccount.mailConfirmationToken}"
            def sysAdmin = mailConfig.sysAdmin
            def regards =  mailConfig.regards
            def theSender = mailConfig.sender
            log.debug("newAccount: ${newAccount.dump()}")
            mailService.sendMail{
                from theSender
                to newAccount.email
                // bcc mailConfig.bcc
                subject message(code:'registration.subject')
                html g.render(template:'confirmMail', model:[appName:mailConfig.appName, confirmationLink:link, teamName:regards, sysAdmin:sysAdmin])
            }
            newAccount.confirmationMailSent
            // return::success
            flash.message = message(code: 'registration.mail.sent')
            return redirect(controller:'portal', action:'landing')
        }
        catch (Exception e) {
            log.debug("registration.fail: " + e.message)
            session.name = params.name
            session.email = params.email
            flash.message = message(code: 'registration.fail', args: [message(code: e.message)])
            return render(view:'register', model:params)
        }
    }

    def confirmRegistration = {
        try{
            def uuid = params.uuid
            if(uuid){
                UserAccount userAccount = UserAccount.findByMailConfirmationToken(uuid)
                if(userAccount){
                    userAccount.enabled = true
                    flash.message = message(code:'registration.complete',
                            args:[grailsApplication.config.registration?.appName,
                                    userAccount.username
                            ])
                    return render(view: 'landing')
                }
                else{
                    throw new RuntimeException('error.missing.account')
                }
            }
            else{
                throw new RuntimeException('error.no.uuid')
            }
        }
        catch (Exception e){
            flash.message = message(code: 'confirmation.fail', args: [message(code: e.message)])
            return render(view:'landing', model:params)
        }
    }

}
