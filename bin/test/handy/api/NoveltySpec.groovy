package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class NoveltySpec extends Specification implements DomainUnitTest<Novelty> {

     void "test domain constraints"() {
        when:
        Novelty domain = new Novelty()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
