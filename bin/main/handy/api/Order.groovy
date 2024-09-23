package handy.api

class Order {
    Integer id
    String order_description
    Long id_client
    String order_status
    BigDecimal discount
    BigDecimal subtotal
    BigDecimal total
    Date create_at = new Date()
    Date update_at  = new Date()
    static hasMany = [productsOrder: ProductOrder] // Relación muchos a muchos a través de OrderProduct
    static belongsTo = Product

    static mapping = {
        version false
        table 't_order'
        products joinTable: [name: 'product_order', key: 'order_id', column: 'product_id']
    }

    static constraints = {
        id_client nullable: true // Permitir que sea nulo
        order_description nullable: false, blank: false
        order_status nullable: false, blank: false
        discount nullable: false, min: 0.0
        subtotal nullable: false, min: 0.0
        total nullable: false, min: 0.0
    }

}