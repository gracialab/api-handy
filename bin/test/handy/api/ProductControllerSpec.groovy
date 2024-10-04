package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ProductControllerSpec extends Specification implements ControllerUnitTest<ProductController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
