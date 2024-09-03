package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class OrderControllerSpec extends Specification implements ControllerUnitTest<OrderController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
