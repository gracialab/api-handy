package handy.api

class Orderp {
    Integer id
    String order_description
    Integer id_client
    String order_status
    BigDecimal discount
    BigDecimal subtotal
    BigDecimal total

    static mapping = {
        version false
    }

    static constraints = {
        id_client nulleable: true
        order_description nullable: false, blank: false
        total nullable: false, min: 0.0
    }
}