package handy.api

import grails.gorm.transactions.Transactional

class OrderService {

    @Transactional
    def saveOrder(Jsondata) {
        try {
            def productIds = Jsondata.productIds
            println(productIds)
            Orderp order = new Orderp(Jsondata as Map)
            // Buscar los productos por sus IDs
            List<Product> products = Product.findAllByIdInList(productIds)
            // Asociar los productos al pedido
            products.each { product ->
                order.addToProducts(product)
            }
            if (order.save(flush: true)) {
               return true
            } else {
               return false
            }
        } catch (Exception ex) {
           println "mensaje: ${ex.getMessage()}"
           ex.printStackTrace()
           return false
        }
    }
}