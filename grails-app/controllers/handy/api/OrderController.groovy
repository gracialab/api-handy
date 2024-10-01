package handy.api

import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder

class OrderController {

    //Call service
    OrderService orderService

    //Endpoint simple, to get order list
    def index() {
        try {
            respond orderList: Order.list()
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }

    //Endpoint to get Users CLIENTS actives
    def getClients() {
        def activeUsers = []
        try {
            activeUsers = User.findAllWhere(active: true)
            .collect { user ->
                [
                        id       : user.id,
                        name     : user.name,
                        lastname : user.lastname,
                        username : user.username,
                        email    : user.email,
                        phone    : user.phone,
                        address  : user.address,
                ]
            }
        } catch (Exception ex) {
            ex.printStackTrace()
        }
        respond activeUsers
    }

    //Endpoint, to get order by Id and return formatted json
    def show(Integer id) {
        def order = Order.get(id)
        if (!order) {
            render status: 404, text: "Order not found"
            return
        }
        JsonBuilder formatOrder = orderService.getOrder(order)
        render formatOrder.toPrettyString()  // Render to JSON
    }

    //Endpoint, to save the order, receive a json in body into request
    @Transactional
    def save() {
        def dataJSON = request.JSON
        def response = orderService.saveOrder(dataJSON)
        if (response.valid) {
            render status: 201, text: "Order saved successfully : ${response.message}"
        } else {
            render status: 400, text: "Failed to save Order : ${response.message}"
        }
    }

    //Endpoint, to update the order, update by id and receive a json in body into request
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

    //Endpoint simple, to delete order
    def delete() {
        def b = Order.get(params.id)
        if (!b) {
            render status: 404, text: "Order not found for id ${params.id}"
        }
    }

    //Endpoint to update status order, receive id and the queryparams receive order_status
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
            if (order.order_status == "CONFIRMADO") {
                response = orderService.saveSalesReceipt(order)
            }
            render status: 201, text: response
        } else {
            render status: 401, text: "Failed to update Order"
        }
    }

    //Endpoint to asign client to order, receive id and the queryparams receive id_client
    @Transactional
    def updateClient(Integer id) {
        def order = Order.get(id)
        order.properties = params
        if (!order) {
            render status: 401, text: "Order not found ID: ${id}"
        } else {
            User client = User.findByIdAndActive(order.id_client, true)
            if (!client) {
                render status: 401, text: "Client not found ID: ${order.id_client} or not active, can you create a new User Client, please"
            } else {
                order.update_at = new Date()
                if (order.save(flush: true)) {
                    render status: 201, text: "Cliente asignado al pedido #${id} - ${order.order_description}"
                } else {
                    render status: 401, text: "Failed to update Order"
                }
            }
        }
    }
}