package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class OrderProductSpec extends Specification implements DomainUnitTest<ProductOrder> {

     void "test domain constraints"() {
        when:
        ProductOrder domain = new ProductOrder()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
