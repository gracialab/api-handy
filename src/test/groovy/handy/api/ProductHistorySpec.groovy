package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductHistorySpec extends Specification implements DomainUnitTest<ProductHistory> {

     void "test domain constraints"() {
        when:
        ProductHistory domain = new ProductHistory()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
