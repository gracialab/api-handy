package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class RoleSpec extends Specification implements DomainUnitTest<Role> {

     void "test domain constraints"() {
        when: "A role is valid"
        Role domain = new Role(name: "Admin", description: "admin")
        then: "the role is validated"
        domain.validate()
     }

    void "test role name is empty"(){
        when: "name is empty"
        Role role = new Role(description: "admin")
        then: "validation throws error"
        !role.validate()
        role.errors['name'].codes.any {it.contains('nullable')}
    }
}
