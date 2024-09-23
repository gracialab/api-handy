package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SalesReceiptSpec extends Specification implements DomainUnitTest<SalesReceipt> {

     void "test domain constraints"() {
        when:
        SalesReceipt domain = new SalesReceipt()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
