package de.dewarim.goblin.landing

import de.dewarim.goblin.RegistrationCheckResult
import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.HighScore
import de.dewarim.goblin.Role
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.UserRole
import org.springframework.web.servlet.support.RequestContextUtils

@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class PortalController extends BaseController {

    def globalConfigService
    def myMailService
    def groovyPageRenderer
    def registrationService
    def userAccountService

    /**
     * The landing page (start page of Little Goblin)
     */

    def landing() {

        return [
                highscore: HighScore.list(max: 5, sort: 'xp', order: 'desc')
        ]
    }

    def index() {
        redirect(action: 'landing')
    }

    def imprint() {

    }

    @Secured(['ROLE_USER'])
    def start() {
        def user = fetchUser()
        if (user.checkRole('ROLE_ADMIN')) {
            redirect(controller: 'admin', action: 'index')
        }
        else {
            return [user       : user,
                    chars      : user.characters?.sort { a, b -> a.name.toLowerCase() <=> b.name.toLowerCase() },
                    createError: params.createError
            ]
        }
    }

    def register() {
        def minNameLength = globalConfigService.fetchValueAsInt('username.min.length', 3)
        def minPassLength = globalConfigService.fetchValueAsInt('password.min.length', 6)
        return [nameOfTheGame: grailsApplication.config.gameName ?: 'Little Goblin',
                username     : params.username?.encodeAsHTML(),
                minPassLength: minPassLength,
                minNameLength: minNameLength,
                problems     : [],
                email        : params.username?.encodeAsHTML(),

        ]
    }

    def doRegister(String username, String password, String email) {
        try {
            RegistrationCheckResult result = registrationService.checkRegistration(params)
            if (!result.isOkay()) {
                render(template: 'registrationForm', model: [username: username, email: email,
                                                             problems: result.problems.collect {
                                                                 message(code: it.messageId, args: it.args)
                                                             }])
                return
            }

            def accountResult = userAccountService.createUserAccount(username, password, email)
            if (!accountResult.isOkay()) {
                def problems = [message(code: 'registration.fail',
                        args: [message(code: accountResult.errorMessage)])]
                render(template: 'registrationForm', model: [username: username, email: email,
                                                             problems: problems])
                return
            }
            def newAccount = accountResult.userAccount
            // send registration mail
            def mailConfig = grailsApplication.config.registration
            def link = "http://${mailConfig.serverUrl ?: 'localhost:8080'}/portal/confirmRegistration?uuid=${newAccount.mailConfirmationToken}"
            def sysAdmin = mailConfig.sysAdmin
            def regards = mailConfig.regards
            def theSender = mailConfig.sender
            log.debug("newAccount: ${newAccount.dump()}")

            def msgBody = groovyPageRenderer.render(template: '/portal/confirmMail',
                    model: [appName : mailConfig.appName, confirmationLink: link,
                            teamName: regards, sysAdmin: sysAdmin, email:email])
            def mailResult = myMailService.sendMail(theSender, [newAccount.email], message(code: 'registration.subject'), msgBody)

            if (mailResult.isOkay()) {
                newAccount.confirmationMailSent = true
                render(template: 'registrationForm', model: [registrationSuccess: true, username: username, email: email])
            }
            else {
                session.name = username
                session.email = email
                render(template: 'registrationForm', model: [username: username, email: email, problems: [message(code: 'registration.fail')]])
            }
        }
        catch (Exception e) {
            log.debug("Failed doRegister:", e)
        }
    }

    def confirmRegistration() {
        try {
            def uuid = params.uuid
            if (uuid) {
                UserAccount userAccount = UserAccount.findByMailConfirmationToken(uuid)
                if (userAccount) {
                    userAccount.enabled = true
                    flash.message = message(code: 'registration.complete',
                            args: [grailsApplication.config.registration?.appName,
                                   userAccount.username
                            ])
                    render(view: 'landing')
                    return
                }
                else {
                    throw new RuntimeException('error.missing.account')
                }
            }
            else {
                throw new RuntimeException('error.no.uuid')
            }
        }
        catch (Exception e) {
            flash.message = message(code: 'confirmation.fail', args: [message(code: e.message)])
            render(view: 'landing', model: params)
        }
    }

}
