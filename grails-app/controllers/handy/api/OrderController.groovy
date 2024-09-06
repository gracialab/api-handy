package handy.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder

class OrderController {

    OrderService orderService;

    def index() {
        def orderList
        try {
            orderList = Order.list()
        } catch (Exception ex) {
            ex.printStackTrace()
        }
        render(Ordenes: orderList)
    }

    def show(Integer id) {
        def order = Order.get(id)
        if (!order) {
            render status: 404, text: "Order not found"
            return
        }
        JsonBuilder formatOrder = orderService.getOrder(order)
        render formatOrder.toPrettyString()  // Renderiza el JSON
    }

    @Transactional
    def save() {
        def dataJSON = request.JSON
        def valid = orderService.saveOrder(dataJSON)
        if (valid) {
            render status: 201, text: 'Order saved successfully'
        } else {
            render status: 400, text: 'Failed to save Order'
        }
    }

    def delete() {
        def b = Order.get(params.id)
        if (!b) {
            render status: 404, text: "Order not found for id ${params.id}"
        }
    }

    // Método para actualizar un pedido
    @Transactional
    def update(Integer id) {
        println("actualizar")
        // Buscar el pedido existente por su ID
        def order = Order.get(id)
        if (!order) {
            // Si el pedido no existe, retornar un error
            flash.message = "Pedido no encontrado con ID: ${id}"
            return
        }
        // Actualizar los campos del pedido con los parámetros recibidos
        order.properties = params
        if (order.save(flush: true)) {
            render status: 201, text: 'Order updated successfully'
        } else {
            render status: 401, text: 'Failed to update Order '
        }
    }

    
}