package de.dewarim.goblin.test

import de.dewarim.goblin.test.crafting.ComponentSpec
import de.dewarim.goblin.test.crafting.ProductSpec
import de.dewarim.goblin.test.crafting.ProductionJobSpec
import de.dewarim.goblin.test.crafting.ProductionServiceSpec
import grails.transaction.Transactional
import org.junit.runner.notification.Failure
import spock.util.EmbeddedSpecRunner

@Transactional
class TestService {

    def testAllTheTings() {
        def specRunner = new EmbeddedSpecRunner()
        specRunner.throwFailure = false
        
        def testClasses = [ItemTypeSpec, ComponentSpec, ProductionJobSpec, ProductionServiceSpec, ProductSpec]
        try {
            def result = specRunner.runClasses(testClasses)
            if (result.failureCount > 0) {

                log.debug("Test Results: ${result.failures.size()} failures.")
                result.failures.each { Failure failure ->
                    log.debug(failure.toString())
                }
                throw new RuntimeException("Tests failed.")
            }
            else{
                log.debug("*********************************")
                log.debug("*** All tests were successful ***")
                log.debug("*********************************")
            }
        } catch (Exception e) {
            log.debug("Tests failed with ", e)
            throw new RuntimeException("Tests failed.")
        }
    }
}
