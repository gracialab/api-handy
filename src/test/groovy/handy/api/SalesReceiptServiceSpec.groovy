package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class SalesReceiptServiceSpec extends Specification implements ServiceUnitTest<SalesReceiptService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
