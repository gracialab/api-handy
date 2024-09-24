package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class LoginControllerSpec extends Specification implements ControllerUnitTest<LoginController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
