package de.dewarim.goblin

/**
 * Base for test classes.
 */

class BaseTest
//extends grails.util.WebTest
{

   def login() {
        ant.group(description:"login first") {
          invoke  '/'
          clickLink 'Login'
          setInputField   name:'j_username', value:'anon'
          setInputField   name:'j_password', value:'anon'
          clickButton     'Login'

          verifyText  'You are logged in as:'
          clickLink   'View your characters'
          verifyText  'Your characters'
        }
    }

}
