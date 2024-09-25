package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class SearchFilterControllerSpec extends Specification implements ControllerUnitTest<SearchFilterController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
