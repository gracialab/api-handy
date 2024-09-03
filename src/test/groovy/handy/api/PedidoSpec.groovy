package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PedidoSpec extends Specification implements DomainUnitTest<Pedido> {

     void "test domain constraints"() {
        when:
        Pedido domain = new Pedido()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
