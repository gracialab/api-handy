package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CategoriaSpec extends Specification implements DomainUnitTest<Categoria> {

     void "test domain constraints"() {
        when:
        Categoria domain = new Categoria()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
