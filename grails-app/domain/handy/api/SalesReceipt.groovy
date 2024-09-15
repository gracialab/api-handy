package handy.api

class SalesReceipt {
    Integer id
    Integer id_order
    Integer id_client
    Date create_at = new Date()

    static constraints = {
    }

    static mapping = {
        version false
    }

}