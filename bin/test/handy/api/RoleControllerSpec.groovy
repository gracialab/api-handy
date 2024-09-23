package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class RoleControllerSpec extends Specification implements ControllerUnitTest<RoleController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
