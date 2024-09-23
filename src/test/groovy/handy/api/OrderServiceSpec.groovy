package handy.api

import grails.converters.JSON
import grails.gorm.transactions.Rollback
import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class OrderServiceSpec extends Specification implements ServiceUnitTest<OrderService>, DomainUnitTest<Order> {

    // Especificar otras clases de dominio adicionales
    Class[] getDomainClassesToMock() {
        [Order, Product]
    }

    @Rollback
    void "test saveOrder with valid JSON"() {
        given: "a valid JSON object"
        def orderJson = [
                order_description : "Order Test",
                id_client : 1005704929,
                order_status : "PENDIENTE",
                discount : 0.0,
                subtotal : 1600000,
                total : 1600000,
                productos : [
                        [
                                id : 6,
                                quantity : 3,
                                discount : 50000,
                                subtotal : 300000,
                                total : 300000
                        ]
                ]
        ]

        when: "the saveOrder service is called"
        def resp = service.saveOrder(orderJson)
        def savedOrder = Order.findByOrder_description("Order Test")
        then: "the order is saved and returned"
        savedOrder != null
        savedOrder.order_description == "Order Test"
        savedOrder.total == 1600000
    }
}
