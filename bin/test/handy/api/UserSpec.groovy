package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User> {

     void "test domain constraints"() {
        when:
        User domain = new User()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
