package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class PermissionControllerSpec extends Specification implements ControllerUnitTest<PermissionController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
