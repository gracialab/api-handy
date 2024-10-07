package handy.api

import java.time.LocalDate

class ReportFilterCommand {
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
}
