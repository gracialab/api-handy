package handy.api

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class EmailServiceSpec extends Specification implements ServiceUnitTest<EmailService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
