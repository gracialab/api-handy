package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class OrderSpec extends Specification implements DomainUnitTest<Orderp> {

     void "test domain constraints"() {
        when:
        Orderp domain = new Orderp()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
