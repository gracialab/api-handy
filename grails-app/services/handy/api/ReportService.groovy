package handy.api

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

class ReportService {
    def sessionFactory

    def generateData(String startDate, String endDate) {
        def session = sessionFactory.currentSession
        def sqlQuery = session.createSQLQuery("""
         SELECT CASE WHEN o.order_status = 'CONFIRMADO' THEN 'FACTURA' ELSE 'PEDIDO' END AS tipo_documento,
            o.id, o.order_description, o.order_status, o.total, u.identification, u.name, u.lastname, u.email 
            FROM t_order o
            LEFT JOIN t_user u ON o.id_client = u.identification
            INNER JOIN sales_receipt s ON s.id_order = o.id
            WHERE o.create_at BETWEEN to_timestamp(:initDate, 'YYYY-MM-DD') AND to_timestamp(:endDate, 'YYYY-MM-DD')
        """)
        sqlQuery.setParameter("initDate", startDate)
        sqlQuery.setParameter("endDate", endDate)

        List results = sqlQuery.list()

        println(results)
        return results
    }

    def generateReport(workbook, results) {
        Sheet sheet = workbook.createSheet("Sales");
        def headers = ['Tipo de documento', 'ID de pedido', 'Descripción', 'Estado', 'Total', 'Identificación del cliente', 'Nombre del cliente', 'Apellidos del cliente', 'Email del cliente']

        // Crea una fila en la hoja
        Row headerRow = sheet.createRow(0);

        headers.eachWithIndex { header, idx ->
            Cell cell = headerRow.createCell(idx)
            cell.setCellValue(header)
            sheet.autoSizeColumn(idx)
        }

        // Llenar filas con datos
        results.eachWithIndex { result, rowIdx ->
            Row dataRow = sheet.createRow(rowIdx + 1)
            result.eachWithIndex { value, colIdx ->
                Cell cell = dataRow.createCell(colIdx)
                cell.setCellValue(value?.toString() ?: "")  // Manejar nulos
            }
        }

    }

}