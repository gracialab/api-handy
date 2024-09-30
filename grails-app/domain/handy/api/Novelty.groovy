package handy.api

import java.time.LocalDate

class Novelty {

    Long id
    Long id_order
    String novelty_type
    String novelty_description
    BigDecimal valor = BigDecimal.ZERO
    LocalDate create_at = LocalDate.now()

    static mapping = {
        version false

    }
    static constraints = {
        id_order nullable: false
        valor nullable: true
        novelty_description nullable: false, blank: false
        novelty_type nullable: false, blank: false, inList: ["DEVOLUCION", "REEMBOLSO", "RECLAMACION", "NOTA_CREDITO"]
    }


}