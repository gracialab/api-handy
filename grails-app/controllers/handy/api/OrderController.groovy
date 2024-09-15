package handy.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder

class OrderController {

    OrderService orderService

    def index() {
        def orderList = []
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
        render formatOrder.toPrettyString()  // Render to JSON
    }

    @Transactional
    def save() {
        def dataJSON = request.JSON
        def response = orderService.saveOrder(dataJSON)
        if (response.valid) {
            render status: 201, text: 'Order saved successfully'
        } else {
            render status: 400, text: "Failed to save Order : ${response.errors}"
        }
    }

    @Transactional
    def update(Integer id) {
        def dataJSON = request.JSON
        def response = orderService.updateOrder(dataJSON, id)
        if (response.valid) {
            render status: 201, text: 'Order saved successfully'
        } else {
            render status: 400, text: "Failed to save Order : ${response.errors}"
        }
    }

    def delete() {
        def b = Order.get(params.id)
        if (!b) {
            render status: 404, text: "Order not found for id ${params.id}"
        }
    }

   @Transactional
    def updateState(Integer id) {
        def order = Order.get(id)
        if (!order) {
            render status: 401, text: "Order not found ID: ${id}"
        }
        order.properties = params
        order.update_at = new Date()
        def response = ""
        if (order.save(flush: true)) {
            if(order.order_status == "CONFIRMADO") {
                response = orderService.saveSalesReceipt(order)
            }
            render status: 201, text: response
        } else {
            render status: 401, text: "Failed to update Order"
        }
    }
}