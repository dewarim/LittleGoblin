package de.dewarim.goblin.shop

import de.dewarim.goblin.BaseTest


class ShopWebTests extends BaseTest {

    // Unlike unit tests, functional tests are sometimes sequence dependent.
    // Methods starting with 'test' will be run automatically in alphabetical order.
    // If you require a specific sequence, prefix the method name (following 'test') with a sequence
    // e.g. test001XclassNameXListNewDelete

   void testShop() {
     login()
     clickLink 'Gobli'
     verifyText 'Shops'
     clickLink  'The General\'s Store'
     verifyText 'Potion of Healing'
     clickLink  'buy'      // note: this may buy any one item, not just the potion.
     verifyText 'You bought'
    }

}