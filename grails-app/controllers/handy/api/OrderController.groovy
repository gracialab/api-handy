package handy.api

import grails.gorm.transactions.Transactional

class OrderController {

    OrderService orderService;

    def index() {
        def orderList
        try {
            orderList = Orderp.list()
        } catch (Exception ex) {
            ex.printStackTrace()
        }
        render(Ordenes: orderList)
    }

    // Este método manejará las solicitudes GET por ID
    def show(Long id) {
        def order = Orderp.get(id)
        if (order) {
            respond order // Envía la representación del libro como respuesta
        } else {
            render status: 404, text: 'Order not found' // Si no se encuentra el libro
        }
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
        def b = Orderp.get(params.id)
        if (!b) {
            flash.message = "Order not found for id ${params.id}"
            redirect(action:list)
        }
    }
}