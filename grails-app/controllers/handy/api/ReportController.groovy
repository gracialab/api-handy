package handy.api

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ReportController {

    ReportService reportService

    def downloadSalesReport() {
        String initDate = params.startDate
        String endDate = params.endDate
        ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream()
        List results = reportService.generateData(initDate, endDate)

        try {
            Workbook workbook = new XSSFWorkbook()
            reportService.generateReport(workbook, results)
            try {
                workbook.write(bytesOutput)
            } finally {
                bytesOutput.close()
            }
            // Preparar respuesta para descargar el archivo
            response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
            response.setHeader("Content-Disposition", "attachment; filename=\"Reporte_de_Ventas.xlsx\"")
            response.outputStream << bytesOutput.toByteArray()  // Escribir los bytes al response
            response.outputStream.flush()
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}