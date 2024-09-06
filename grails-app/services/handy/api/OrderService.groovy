package handy.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder

class OrderService {

    @Transactional
    def saveOrder(Jsondata) {
            def order = new Order(Jsondata as Map)
            def productsData = Jsondata.productos
            if (!order.save(flush: true)) {
                throw new RuntimeException("Error al guardar el pedido: ${order.errors}")
            }
            productsData.each { productData ->
                def product = Product.get(productData.id)
                if (!product) {
                    throw new RuntimeException("Producto no encontrado")
                }
                def ProductOrder = new ProductOrder(
                        product: product,
                        order: order,
                        quantity: productData.quantity,
                        discount: productData.discount,
                        subtotal: productData.subtotal,
                        total: productData.total
                )
                if (!ProductOrder.save(flush: true)) {
                    throw new RuntimeException("Error al guardar")
                }
            }

            return true
    }

    def validProduct(productsData) {
        productsData.each { productData ->
            def product = Product.get(productData.id)
            if (!product) {
                throw new RuntimeException("Producto no encontrado")
            }
        }
    }

    def getOrder(order) {
        JsonBuilder json = new JsonBuilder()
        return json {
            cliente order.id_client?:"No asignado"
            orderDate order.create_at
            discounts order.discount
            subtotal order.subtotal
            total order.total
            productos order.orderProducts.collect { op ->
                [productId: op.product.id, productName: op.product.name,
                 quantity: op.quantity, discount: op.discount,
                 subtotal: op.subtotal, total: op.total]
            }
        }
    }


}