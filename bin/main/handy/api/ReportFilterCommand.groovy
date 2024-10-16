package handy.api

import java.time.LocalDate

class ReportFilterCommand {
    String startDate
    String endDate

    static constraints = {
        startDate nullable: false
        endDate nullable: false
    }

    ReportFilterCommand(String startDate, String endDate) {
        this.startDate = startDate
        this.endDate = endDate
    }

}
