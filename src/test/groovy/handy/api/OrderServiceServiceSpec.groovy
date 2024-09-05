package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class OrderServiceServiceSpec extends Specification implements ServiceUnitTest<OrderService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
