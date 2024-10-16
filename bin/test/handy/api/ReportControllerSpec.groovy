package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ReportControllerSpec extends Specification implements ControllerUnitTest<ReportController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
