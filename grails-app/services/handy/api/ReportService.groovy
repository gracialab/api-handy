package handy.api

import grails.gorm.transactions.Transactional

import java.time.LocalDate


//import grails.plugin.excelexport.ExcelExporter


class ReportService {
    def sessionFactory

    def doSomething() {

    }


    @Transactional
    def exportSalesReport(ReportFilterCommand filterCommand) {
        /*def dataReport = []
        def query = Order.createCriteria()
        def orders = query.list {
            if (filterCommand.startDate) {
                ge("create_at", filterCommand.startDate)
            }
            if (filterCommand.endDate) {
                le("create_at", filterCommand.endDate)
            }
               createAlias('user', 'user') // Alias 'c' para la tabla User
             //  eq('id_client', 1005704929)
            createAlias('SalesReceipt', 'sales') // Alias 'sales' para la tabla Sales Receipt
            eq('sales.id_order', 'id')
            // Proyecciones para seleccionar campos específicos
            projections {
                property('id_order', 'id')           // ID del pedido
                property('user.name', 'clientName')   // Nombre del producto
                property('sales.id', 'SalesId') // Precio del producto
                property('order.total', 'total')  // Nombre del cliente
            }

            // Ordenar los resultados (opcional)
            order('id', 'asc')

        }
        */
        def session = sessionFactory.currentSession
        def sqlQuery = session.createSQLQuery("""
            SELECT o.id, o.order_description, u.name, u.lastname 
            FROM t_order o
            LEFT JOIN t_user u ON o.id_client = u.identification
            LEFT JOIN sales_receipt s ON s.id_order = o.id
        """)
        /// sqlQuery.setParameter("status", "PENDIENTE")
        def results = sqlQuery.list()
        println(results)
        return results
    }

}