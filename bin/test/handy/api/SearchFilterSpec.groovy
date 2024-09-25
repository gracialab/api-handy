package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SearchFilterSpec extends Specification implements DomainUnitTest<SearchFilter> {

     void "test domain constraints"() {
        when:
        SearchFilter domain = new SearchFilter()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
