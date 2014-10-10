package de.dewarim.goblin

class RegistrationService {
    
    static transactional = false
    
    def inputValidationService
    def globalConfigService
    
    def checkRegistration(Map params) {
        def checkResult = new RegistrationCheckResult()
        if (params.username?.trim()?.length() == 0) {
            checkResult.addProblem('registration.bad.name')
            return checkResult
        }
        // check if username is valid
        def username = inputValidationService.checkAndEncodeText(params, "username", "registration.username")
        def minNameLength = globalConfigService.fetchValueAsInt('username.min.length', 3)
        if (username.length() < minNameLength) {
            checkResult.addProblem('registration.short.username', [minNameLength])
        }
        // check if passwords match
        def password = params.password
        def password2 = params.password2
        if (password != password2) {
            checkResult.addProblem('registration.password.mismatch')
        }
        def minPassLength = globalConfigService.fetchValueAsInt('password.min.length', 6)
        if (password.length() < minPassLength) {
            checkResult.addProblem('registration.short.password', [minPassLength])
        }

        // check if email at least looks like it may be a valid address
        String email = params.email
        if (!email || !email.contains('@')) {
            checkResult.addProblem('registration.bad.email')
        }

        // check if we know this email
        UserAccount existingUser = UserAccount.findByEmail(email.toLowerCase())
        if (existingUser) {
            log.debug("user with email $email already exists as ${existingUser.username}")
            checkResult.addProblem('registration.used.email')
        }
        //log.debug("registrationCheckResult:"+checkResult)
        return checkResult
    }
}
