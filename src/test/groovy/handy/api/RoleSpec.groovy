package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class RoleSpec extends Specification implements DomainUnitTest<Role> {

     void "test domain constraints"() {
        when:
        Role domain = new Role()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
