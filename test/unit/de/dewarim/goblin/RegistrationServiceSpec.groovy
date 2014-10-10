package de.dewarim.goblin

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RegistrationService)
@Mock([UserAccount, GlobalConfigEntry])
class RegistrationServiceSpec extends Specification {

    def registrationService = new RegistrationService()
    
    def setup() {
        registrationService.inputValidationService = new InputValidationService()
        registrationService.globalConfigService = new GlobalConfigService()
    }

    def cleanup() {
    }

    void "happyPathTest"() {
        
        given:
        def params = [username:'foo', password:'foobar', password2:'foobar', email:'x@example.com']
        RegistrationCheckResult checkResult = registrationService.checkRegistration(params)
        
        expect:
        checkResult.isOkay()
        
    }
    
    void "emptyNameTest"(){
        given:
        def params = [username:'', password:'foobar', password2:'foobar', email:'x@example.com']
        RegistrationCheckResult checkResult = registrationService.checkRegistration(params)

        expect:
        !checkResult.isOkay()
        checkResult.problems.find{it.messageId.equals('registration.bad.name')}
    }   
    
    void "existingEmailTest"(){
        given:
        def user = new UserAccount(username:'Gandalf', userRealName: 'Mithrandir', 
                passwd: 'mellon', email:'gandalf@example.com')
        user.save()
        def params = [username:'bilbo', password:'teabag', password2:'teabag', email:'gandalf@example.com']
        RegistrationCheckResult checkResult = registrationService.checkRegistration(params)

        expect:
        !checkResult.isOkay()
        checkResult.problems.find{it.messageId.equals('registration.used.email')}
    }
        
    void "badParamsTest"(){
        given:
        def params = [username:'f', password:'foob', password2:'xxx', 
                      email:'example.com']
        RegistrationCheckResult checkResult = registrationService.checkRegistration(params)

        expect:
        !checkResult.isOkay()
        checkResult.problems.find{it.messageId.equals('registration.short.username')}
        checkResult.problems.find{it.messageId.equals('registration.password.mismatch')}
        checkResult.problems.find{it.messageId.equals('registration.short.password')}
        checkResult.problems.find{it.messageId.equals('registration.bad.email')}
    }
    
    
}
