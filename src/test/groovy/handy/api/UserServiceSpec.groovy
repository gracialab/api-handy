package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UserServiceSpec extends Specification implements ServiceUnitTest<UserService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
