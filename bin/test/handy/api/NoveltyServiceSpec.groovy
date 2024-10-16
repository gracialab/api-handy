package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class NoveltyServiceSpec extends Specification implements ServiceUnitTest<NoveltyService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
