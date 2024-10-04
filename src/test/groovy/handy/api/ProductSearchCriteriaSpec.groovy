package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ProductSearchCriteriaSpec extends Specification implements DomainUnitTest<ProductSearchCriteria> {

     void "test domain constraints"() {
        when:
        ProductSearchCriteria domain = new ProductSearchCriteria()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
