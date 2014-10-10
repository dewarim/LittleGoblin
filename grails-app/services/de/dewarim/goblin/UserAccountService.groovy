package de.dewarim.goblin

import grails.transaction.Transactional

@Transactional
class UserAccountService {

    AccountCreationResult createUserAccount(String username, String password, String email){
        try {
            UserAccount newAccount = new UserAccount(username: username, email: email, userRealName: username)
            newAccount.passwd = password
            newAccount.save()
            Role role = Role.findByName('ROLE_USER')
            UserRole userRole = new UserRole(newAccount, role)
            userRole.save()
            return new AccountCreationResult(userAccount: newAccount)
        }
        catch (Exception e){
            log.debug("Failed to create account: "+e.getMessage())
            return new AccountCreationResult(errorMessage: e.getMessage())
        }
    }
}
