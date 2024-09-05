package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class OrderProductSpec extends Specification implements DomainUnitTest<OrderProduct> {

     void "test domain constraints"() {
        when:
        OrderProduct domain = new OrderProduct()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
