package de.dewarim.goblin

import de.dewarim.goblin.BaseTest

class FightWebTests extends BaseTest {

    // Unlike unit tests, functional tests are sometimes sequence dependent.
    // Methods starting with 'test' will be run automatically in alphabetical order.
    // If you require a specific sequence, prefix the method name (following 'test') with a sequence
    // e.g. test001XclassNameXListNewDelete

   void testFlight() {
     login()
     clickLink  'Gobli'
     clickLink  'Punch the Puppet'
     verifyText 'Accept the quest'
     clickLink  'Punch the Puppet'
     verifyText 'Your opponent'
     clickLink  'Flee'
     verifyText 'You run away!'
     clickLink  'Return to town'
     verifyText 'Available quests'

     // test bug fix
     invoke     url:'/', description:'Test bug #2992011 / Combat does not always end after flight.'
     clickLink  'View your characters'
     clickLink  'Gobli'
     verifyText 'Rootanum'
    }

}