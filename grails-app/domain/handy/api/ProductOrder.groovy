package handy.api

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ProductOrder implements Serializable {

    Order order
    Product product
    Integer quantity
    BigDecimal discount
    BigDecimal subtotal
    BigDecimal total
    Date create_at = new Date()
    Date update_at  = new Date()

    static constraints = {
        quantity nullable: false, min: 1
        discount nullable: false,  min: 0.0
        subtotal nullable: false, min: 0.0, scale: 2
        total nullable: false, min: 0.0, scale: 2

    }
    static mapping = {
        id composite: ['product', 'order']  // Configura la clave compuesta de Order y Product
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (!(o instanceof ProductOrder)) return false
        ProductOrder that = (ProductOrder) o
        return order?.id == that.order?.id && product?.id == that.product?.id
    }

    @Override
    int hashCode() {
        return Objects.hash(order?.id, product?.id)
    }

}