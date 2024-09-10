package handy.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

class OrderService {

    @Transactional
    def saveOrder(json) {
        def response = new HashMap<>()
        try {
            def order = new Order(json as Map)
            List<ObjectError> errors = new ArrayList<>()
            if (!order.validate()) {
                return validOrder(order, errors, response)
            } else if (!order.save(flush: true)) {
                throw new RuntimeException("Failed save the order: ${order.errors}")
            }
            json.productos.each { productData ->
                validAndSaveProduct(order, productData)
            }
            response.put("valid", true)
            return response
        } catch (Exception e) {
            e.printStackTrace()
            response.put("valid", false)
            response.put("errors", e.getMessage())
            return response
        }
    }

    def validOrder(order, errors, response) {
        order.errors.allErrors.each {
            if (it instanceof FieldError) {
                errors.add("Field with error: ${it.field}, Message: ${it.defaultMessage}")
            } else {
                errors.add("Error: ${it.defaultMessage}")
            }
        }
        response.put("errors", errors)
        response.put("valid", false)
        return response
    }

    def validAndSaveProduct(order, productData){
        def product = Product.get(productData.id)
        if (!product) {
            throw new RuntimeException("Product not found")
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
            throw new RuntimeException("Error to save")
        }
    }

    def updateOrder(json, id) {
        def response = new HashMap<>()
        try {
            Order order = Order.get(id)
            order.properties = json
            if (!order) {
               response.put("valid", false)
               response.put("text", "Order not found ID: $id")
               return response
            } else if (!order.save(flush: true)) {
                response.put("valid", false)
                response.put("text", "Failed save the order: ${order.errors}")
                return response
            }
            json.productos.each { productData ->
                Product product1 = Product.get(productData.id)
                def product = ProductOrder.findByOrderAndProduct(order, product1)
                product.properties = productData
                if (!product.save(flush: true)) {
                    throw new RuntimeException("Error to save")
                }
            }
            response.put("valid", true)
            response.put("text", "Order update success")
            return response
        } catch (Exception ex) {
            response.put("valid", false)
            response.put("text", "Error to update order: $id : $ex.message")
            return response
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
            productos order.productsOrder.collect { op ->
                [productId: op.product.id, productName: op.product.name,
                 quantity: op.quantity, discount: op.discount,
                 subtotal: op.subtotal, total: op.total]
            }
        }
    }

}