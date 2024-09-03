package handy.api

import groovy.transform.CompileStatic

@CompileStatic
class OrderService {

    def saveOrder(data) {
        try {
           println(data)
           Object objetoOrder = data
           def productos = objetoOrder.getAt("productos")
           println(productos)
           return true
        } catch (Exception ex) {
           println "mensaje: ${ex.getMessage()}"
           ex.printStackTrace()
           return false
        }
    }
}