package de.dewarim.goblin

/**
 */
class AccountCreationResult {
    
    UserAccount userAccount
    String errorMessage
    
    boolean isOkay(){
        return userAccount != null
    }
    
}
