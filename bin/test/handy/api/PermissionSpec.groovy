package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PermissionSpec extends Specification implements DomainUnitTest<Permission> {

     void "test domain constraints"() {
        when:
        Permission domain = new Permission()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
