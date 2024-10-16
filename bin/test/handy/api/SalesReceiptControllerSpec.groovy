package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class SalesReceiptControllerSpec extends Specification implements ControllerUnitTest<SalesReceiptController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
