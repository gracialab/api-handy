package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class OrderSpec extends Specification implements DomainUnitTest<Order> {

     void "test domain constraints"() {
        when:
        Order domain = new Order()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
