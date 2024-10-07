package handy.api

import java.time.LocalDate

class ReportSalesDTO {

    LocalDate startDate
    LocalDate endDate
    Integer countSales
    Integer countOrders


    static constraints = {
        startDate nullable: false
        endDate nullable: false
        countSales nullable: true, blank: true
        countOrders nullable: true, min: 0
    }

    ReportSalesDTO(LocalDate endDate, LocalDate startDate, Integer countSales, Integer countOrders) {
        this.endDate = endDate
        this.startDate = startDate
        this.countSales = countSales
        this.countOrders = countOrders
    }
}
