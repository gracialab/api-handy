package handy.api

import java.time.LocalDate

class OrderDTO {
    Long idOrder
    List<ProductOrderDTO> products
    LocalDate dateOrder
    String status
    BigDecimal total

    OrderDTO() {}

    OrderDTO(Long idOrder, List<ProductOrderDTO> products, LocalDate dateOrder, String status, BigDecimal total) {
        this.idOrder = idOrder
        this.products = products
        this.dateOrder = dateOrder
        this.status = status
        this.total = total
    }
}
