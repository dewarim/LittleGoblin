  // Suite for all webtests in this application.

import grails.util.WebTest

class TestSuite extends WebTest {

    static void main(args) {
   		new TestSuite().runTests(args)
    }

    /**
        Scan trough all test files and call their suite method.
    */
    void suite() {
    	def scanner = ant.fileScanner {
           	fileset(dir: WEBTEST_DIR, includes:'**/*Tests.groovy')
        }
        for (file in scanner) {
            def test = getClass().classLoader.parseClass(file).newInstance()
            test.ant = ant
            test.suite()
        }
    }

/*
    You can alternatively define your suite manually by calling a MyTest.groovy like so
    instead of the suite impl. above:

        new MyTest(ant:ant).suite()
        new MyOtherTest(ant:ant).suite()

    This gives you more fine-grained control over the sequence of test execution.
*/

}