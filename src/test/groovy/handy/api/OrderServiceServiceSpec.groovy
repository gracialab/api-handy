package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class OrderServiceServiceSpec extends Specification implements ServiceUnitTest<OrderServiceService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
