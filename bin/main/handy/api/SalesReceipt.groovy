package handy.api

import java.time.LocalDate

class SalesReceipt {
    Integer id
    Integer id_order
    Long id_client
    LocalDate create_at = LocalDate.now()

    static constraints = {
    }

    static mapping = {
        version false
    }

}