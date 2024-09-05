package handy.api

class Product {
    Integer id
    String name
    String product_description
    BigDecimal price
    Integer stock
    Date create_at = new Date()
    Date update_at  = new Date()

    static hasMany = [order: Orderp]

    static mapping = {
        version false
        order joinTable: [name: 'product_order', key: 'product_id', column: 'order_id']
    }
}