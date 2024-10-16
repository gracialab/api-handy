package handy.api

import java.time.LocalDate

class Order {
    Integer id
    String order_description
    Long id_client
    String order_status
    BigDecimal discount
    BigDecimal subtotal
    BigDecimal total
    LocalDate create_at = LocalDate.now()
    LocalDate update_at =  LocalDate.now()
    static hasMany = [productsOrder: ProductOrder, novelties : Novelty] // Relación muchos a muchos a través de OrderProduct
    static belongsTo = Product

    static mapping = {
        version false
        table 't_order'
        products joinTable: [name: 'product_order', key: 'order_id', column: 'product_id']
        novelties joinTable: [name: 'novelty_order', key: 'id', column: 'id_order']
    }

    static constraints = {
        novelties nullable: true
        id_client nullable: true
        order_description nullable: false, blank: false
        order_status nullable: false, blank: false, inList: ["PENDIENTE", "CONFIRMADO", "ENVIADO", "COMPLETADO"]
        discount nullable: false, min: 0.0
        subtotal nullable: false, min: 0.0
        total nullable: false, min: 0.0
    }

}