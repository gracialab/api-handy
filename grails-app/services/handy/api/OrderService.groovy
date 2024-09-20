package handy.api

import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService
import groovy.json.JsonBuilder
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

class OrderService {

    MailService mailService

    //Service to save order with products in the table relational product_order
    @Transactional
    def saveOrder(json) {
        def response = new HashMap<>()
        def resp = new HashMap<>()
        try {
            def order = new Order(json as Map)
            List<ObjectError> errors = new ArrayList<>()
            if (!order.validate()) {
                return validOrder(order, errors, response)
            } else if (!order.save(flush: true)) {
                throw new RuntimeException("Failed save the order: ${order.errors}")
            }
            json.productos.each { productData ->
                resp.put("text", validAndSaveProduct(order, productData))
            }
            response.put("message", resp)
            response.put("valid", true)
            return response
        } catch (Exception e) {
            e.printStackTrace()
            response.put("valid", false)
            response.put("message", e.getMessage())
            return response
        }
    }

    //Method to valid the order
    def validOrder(order, errors, response) {
        order.errors.allErrors.each {
            if (it instanceof FieldError) {
                errors.add("Field with error: ${it.field}, Message: ${it.defaultMessage}")
            } else {
                errors.add("Error: ${it.defaultMessage}")
            }
        }
        response.put("message", errors)
        response.put("valid", false)
        return response
    }

    //Method to valid an save the product_order
    def validAndSaveProduct(order, productData) {
        def product = Product.get(productData.id)
        if (!product) {
            return "Product not found"
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
            return "Error to save"
        }
        return "Save order product"
    }

    //Service to update order, receive id
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

    //Service to get order, returns order formated
    def getOrder(order) {
        JsonBuilder json = new JsonBuilder()
        return json {
            cliente order.id_client ?: "No asignado"
            orderDate order.create_at
            discounts order.discount
            subtotal order.subtotal
            total order.total
            productos order.productsOrder.collect { op ->
                [productId: op.product.id, productName: op.product.name,
                 quantity : op.quantity, discount: op.discount,
                 subtotal : op.subtotal, total: op.total]
            }
        }
    }

    //Service to send email
   def sendMail(Order order) {
       try {
           mailService.sendMail {
               to 'valentinarvpe@gmail.com'
               from 'saleshand7@gmail.com'
               subject 'Notificación - Estado de tú pedido'
               text "¡Hola!, ${order.id_client}  \n" +
                       "Tu pedido #${order.id} ha cambiado de estado, " +
                       "se encuentra ${order.order_status} \n" +
                       "¡Gracias por confiar en nosotros!"
           }
           return "Send email successfully"
       } catch(Exception ex){
           ex.printStackTrace()
           return "Failed to send email"
       }
    }

    //Method to save the sales receipt, receive order
    def saveSalesReceipt(Order order) {
        try {
            def salesReceipt = new SalesReceipt()
            salesReceipt.id_order = order.id
            salesReceipt.id_client = order.id_client
            if (!salesReceipt.save(flush:true)) {
               return "Failed to save Sales of receipt"
            }
            def response =sendMail(order)
            return "Order status updated successfully:  - ${response}"
        } catch (Exception ex) {
            ex.printStackTrace()
            return "Failed to save Sales of receipt"
        }
    }

}