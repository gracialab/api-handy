package handy.api
import grails.converters.JSON

class SalesReceiptController {

    //Call service
    SalesReceiptService salesReceiptService

    //Endpoint, to get order by Id and return formatted json
    def getSalesReceipt(Integer id) {
        def salesReceipt = SalesReceipt.get(id)
        if (!salesReceipt) {
            render status: 404, text: "Sales receipt not found"
            return
        }
        def formatSalesReceipt = salesReceiptService.getSalesReceipt(salesReceipt)
        render formatSalesReceipt as JSON
    }

    // Generate pdf
    def generatePdf(Integer id) {
        def salesReceipt = SalesReceipt.get(id)
        if (!salesReceipt) {
            render status: 404, text: "Sales receipt not found"
            return
        }
        def SalesReceipt = salesReceiptService.getSalesReceipt(salesReceipt)
        try {
            renderPdf(template: "templates/receipts", model: [receipt: SalesReceipt])
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }

    def sendEmail(Integer id) {
        def salesReceipt = SalesReceipt.get(id)
        if (!salesReceipt) {
            render status: 404, text: "Sales receipt not found"
            return
        }
        def salesReceiptDTO = salesReceiptService.getSalesReceipt(salesReceipt)
        def resp = salesReceiptService.sendMail(salesReceiptDTO)
        render status: 201, text: resp
    }
}