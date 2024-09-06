package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
