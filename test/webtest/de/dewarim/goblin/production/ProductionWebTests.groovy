package de.dewarim.goblin.production

import de.dewarim.goblin.BaseTest

class ProductionWebTests extends BaseTest {

    // Unlike unit tests, functional tests are sometimes sequence dependent.
    // Methods starting with 'test' will be run automatically in alphabetical order.
    // If you require a specific sequence, prefix the method name (following 'test') with a sequence
    // e.g. test001XclassNameXListNewDelete

   void testProductionOfIronBars() {
       login()
       clickLink 'Gobli'
       clickLink 'Academies'
       clickLink 'Smiths of Ore'
       3.times {clickLink 'start learning'}
       Thread.sleep(1500)
       invoke '/cron/learning'
       invoke '/town/show'
       clickLink 'Visit the workshop'
       clickLink 'Base Material'
       clickLink 'Select components'

       // this will only work while the item_1 is iron ore.
       3.times {
           setInputField name:'item_1', value:'3'
           clickButton  'Add product to queue'
           
           verifyText   'will be created as soon as possible'
       }
       clickLink    'View job list'
       verifyText   'Your crafting queue'
       3.times{
           clickLink    'cancel job'
           verifyText   'Activity canceled'
       }
       verifyText   'You got nothing to do at the moment.'

    }

}