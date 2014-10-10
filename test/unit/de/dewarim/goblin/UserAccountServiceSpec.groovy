package de.dewarim.goblin

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserAccountService)
@Mock([UserAccount, Role, UserRole])
class UserAccountServiceSpec extends Specification {

    UserAccountService userAccountService = new UserAccountService()

    def setup() {
        // Must mock transactionManager:
        // see: https://jira.grails.org/browse/GRAILS-10538
        userAccountService.transactionManager = Mock(PlatformTransactionManager) {
            getTransaction(_) >> Mock(TransactionStatus)
        }
        Role role = new Role(name: 'ROLE_USER', description: '---')
        role.save()
    }

    def cleanup() {

    }

    void "happyPathTest"() {

        given:
        def accountResult = userAccountService.createUserAccount('a username', 'a password', 'email@example.com')

        expect:
        accountResult.isOkay()

    }
    
    void "duplicateNameTest"() {

        given:
        def result1 = userAccountService.createUserAccount('a username', 'a password', 'email@example.com')
        def result2 = userAccountService.createUserAccount('a username', 'a password', 'email@example.com')

        expect:
        result1.isOkay()
        !result2.isOkay()

    }
}
