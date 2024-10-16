package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class NoveltyControllerSpec extends Specification implements ControllerUnitTest<NoveltyController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
