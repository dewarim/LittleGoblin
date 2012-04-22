package de.dewarim.goblin

class EquipWebTests extends BaseTest {

    // Unlike unit tests, functional tests are sometimes sequence dependent.
    // Methods starting with 'test' will be run automatically in alphabetical order.
    // If you require a specific sequence, prefix the method name (following 'test') with a sequence
    // e.g. test001XclassNameXListNewDelete

    void testShopAndEquip() {
        login()
        clickLink 'Gobli'
        clickLink 'The General\'s Store'
        // buy three torches:
        (0..2).each{clickLink href: 'weapon.torch'}

        clickLink   'Return to town'
        clickLink   'Punch the Puppet'
        verifyText  'Accept the quest'
        clickLink   'Punch the Puppet'
        verifyText  'Your opponent'

        (0..2).each{ clickLink 'equip' }

        verifyText 'You need to take something off'

        // finish the quest
        clickLink  'Flee'
        verifyText 'You run away!'
        clickLink  'Return to town'
        verifyText 'Available quests'
    }

}