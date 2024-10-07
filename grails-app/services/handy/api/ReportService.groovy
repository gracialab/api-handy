package handy.api

import grails.gorm.transactions.Transactional
import org.apache.commons.lang.time.DateUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.time.LocalDate
import java.time.LocalDateTime

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

        // Rellenar los encabezados en la fila 0
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            sheet.autoSizeColumn(i)
        }

        for (int i = 0; i < results.size(); i++) {
            Row dataRow = sheet.createRow(i + 1)
            Cell[] cell = new Cell[headers.size()]
            for (int j = 0; j < cell.size(); j++) {
                cell[j] = dataRow.createCell(j)
            }
            cell[0].setCellValue(results.get(i)[0])
            cell[1].setCellValue(results.get(i)[1])
            cell[2].setCellValue(results.get(i)[2])
            cell[3].setCellValue(results.get(i)[3])
            cell[4].setCellValue(results.get(i)[4])
            cell[5].setCellValue(results.get(i)[5])
            cell[6].setCellValue(results.get(i)[6])
            cell[7].setCellValue(results.get(i)[7])
            cell[8].setCellValue(results.get(i)[8])
        }
    }

}