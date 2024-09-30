package handy.api

import groovy.transform.CompileStatic
import org.springframework.validation.FieldError

@CompileStatic
class NoveltyService {

    def saveNovelty(json) {
        try {
            def novelty = new Novelty(json as Map)
            def map = new ArrayList<>()
            if (!novelty.validate()) {
                return messageResponseErrors(false, "Error en validaciones", novelty.errors.allErrors)
            }
            switch (novelty.novelty_type) {
                case "DEVOLUCION":
                    return saveNoveltyDevolution(novelty, map)
                case "REEMBOLSO":
                    return saveNoveltyRepayment(novelty, map)
                default:
                    novelty.save(flush: true)
                    return messageResponse(true, "Novedad registrada correctamente", novelty)
            }
        } catch (Exception ex) {
            return [valid   : false, mensaje : "Error al registrar la novedad ${ex.getMessage()}"]
        }
    }
    def saveNoveltyRepayment(Novelty novelty, List map){
        try {
            if(novelty.valor <= 0){
                map.add("El valor del reembolso es requerido")
                return messageResponse(false, "No se pudo registrar la novedad tipo ${novelty.novelty_type}", map)
            } else {
                novelty.save(flush: true)
                return messageResponse(true, "Novedad ${novelty.novelty_description} registrada correctamente", novelty)
            }
        } catch (Exception ex){
            ex.printStackTrace()
        }
    }
    def saveNoveltyDevolution(Novelty novelty, List map) {
        try {
            adjustStock(novelty.id_order, map, true)
            novelty.save(flush: true)
            return messageResponse(true, "Novedad registrada correctamente", map)
        } catch (Exception ex){
            return messageResponse(false, "No se pudo registrar la novedad tipo ${novelty.novelty_type}", ex.getMessage())
        }
    }
    def updateInventory(long id_order) {
        def map = new ArrayList<>()
        try {
            List<Novelty> novelties = Novelty.findAllWhere(id_order: id_order)
            if (novelties.size() > 0){
                novelties.each { it -> {
                    if (it.novelty_type == "DEVOLUCION"){
                        adjustStock(id_order, map, false)
                    }
                }}
            }
            return messageResponse(true, "Se ajustó correctamente", map)
        } catch (Exception ex) {
            return [valid   : false, mensaje : "Error al ajustar inventario ${ex.getMessage()}"]
        }
    }
    def adjustStock(long id_order, List map, subtract){
        Order order  = Order.get(id_order)
        order.productsOrder.each {productOrder-> {
            Product product = Product.get(productOrder.product.id)
            product.setStock(subtract? product.stock - productOrder.quantity : product.stock + productOrder.quantity)
            product.save(flush:true)
            map.add("Se actualizó el inventario con ${productOrder.quantity} unidades del producto ${product.id}, ahora su stock es de ${product.stock}")
        }}
    }
    def messageResponseErrors(valid, message, allErrors) {
      return [valid  : valid,
         mensaje: message,
         errores: allErrors.collect { error ->
             if (error instanceof FieldError) {
                 [field  : error.field,
                  message: error.defaultMessage]
             } else {
                 [message: error.toString()]
             }
         }]
    }
    def messageResponse(valid, message, data) {
        return [valid: valid, mensaje : message, data: data]
    }
}