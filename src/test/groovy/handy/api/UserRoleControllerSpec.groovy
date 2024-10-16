package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class UserRoleControllerSpec extends Specification implements ControllerUnitTest<UserRoleController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
