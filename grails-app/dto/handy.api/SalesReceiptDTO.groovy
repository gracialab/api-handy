package handy.api

import java.time.LocalDate

class SalesReceiptDTO {
    Long idSalesReceipt
    OrderDTO order
    UserDTO client
    LocalDate dateOrder
    String status
    BigDecimal total

    SalesReceiptDTO() {}

    SalesReceiptDTO(Long idOrder, UserDTO client, OrderDTO order, String status, LocalDate dateOrder, BigDecimal total) {
        this.idSalesReceipt = idOrder
        this.client = client
        this.order = order
        //this.products = productos
        this.dateOrder = dateOrder
        this.status = status
        this.total = total
    }
}