package handy.api

import grails.plugins.export.ExportService

import java.time.LocalDate


class ReportController {

    ExportService exportService
    ReportService reportService

    def downloadSalesReport(ReportFilterCommand filterCommand) {
        //def request = request.JSON
        // Simulamos datos de ventas
        def salesData = [
                [id: 1, cliente: "Juan                                                                                                                                                                                                                                                                                                                                                       Pérez", fecha: new Date(), total: 250.00],
                [id: 2, cliente: "Ana López", fecha: new Date(), total: 150.00],
                [id: 3, cliente: "Carlos Gómez", fecha: new Date(), total: 300.00]
        ]
        reportService.exportSalesReport(filterCommand)

        // Definir las columnas del archivo Excel
        def headers = ['ID Pedido', 'Cliente', 'Fecha', 'Total']

        // Definir los nombres de los campos de los datos
        def fields = ['id', 'cliente', 'fecha', 'total']

        // Configurar el tipo de contenido de la respuesta para descargar un archivo Excel
        response.contentType = 'application/vnd.ms-excel'
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte_ventas.xls\"")

        // Crear un mapa de mapeo de campos: headers y fields deben estar alineados
        Map fieldMappings = [
                (headers[0]): 'id',
                (headers[1]): 'cliente',
                (headers[2]): 'fecha',
                (headers[3]): 'total'
        ]

        // Usar el servicio de exportación para generar el archivo Excel
        exportService.export('excel', response.outputStream, salesData, fieldMappings, [:])

        // Asegurarse de que el flujo de salida esté cerrado
        response.outputStream.flush()
        response.outputStream.close()
    }


}