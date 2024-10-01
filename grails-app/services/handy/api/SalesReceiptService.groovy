package handy.api

import grails.plugins.mail.MailService
import grails.plugins.rendering.pdf.PdfRenderingService
import groovy.transform.CompileStatic

@CompileStatic
class SalesReceiptService {

    EmailService emailService
    PdfRenderingService pdfRenderingService // Inyección del servicio

    def getSalesReceipt(SalesReceipt salesReceipt) {
        OrderDTO orderDTO = setOder(salesReceipt)
        UserDTO clientDTO = setClient(salesReceipt)
        SalesReceiptDTO SalesDTO = new SalesReceiptDTO(salesReceipt.id, clientDTO, orderDTO, orderDTO.status, salesReceipt.create_at, orderDTO.total)
        return SalesDTO
    }

    OrderDTO setOder(SalesReceipt salesReceipt) {
        Order order = Order.get(salesReceipt.id_order)
        List<ProductOrderDTO> products = setProductOrderDTOList(order)
        OrderDTO orderDTO = new OrderDTO(order.id, products, order.create_at, order.order_status, order.total)
        return orderDTO;
    }

    UserDTO setClient(SalesReceipt salesReceipt) {
        User user = User.get(salesReceipt.id_client)
        UserDTO clientDTO = new UserDTO(user.id, user.name, user.lastname, user.email, user.address)
        return clientDTO;
    }

    List<ProductOrderDTO> setProductOrderDTOList(Order order) {
        List<ProductOrderDTO> productOrderDTOList = []
        order.productsOrder.each { p ->
            {
                ProductOrderDTO productDTO = new ProductOrderDTO(p.product.id, p.product.name, p.product.price, p.quantity)
                productOrderDTOList.add(productDTO)
            }
        }
        return productOrderDTOList
    }

    def sendMail(SalesReceiptDTO salesReceiptDTO) {
        ByteArrayOutputStream bytes2
        bytes2 = pdfRenderingService.render(template: "/templates/receipts",
                model: [receipt: salesReceiptDTO]) as ByteArrayOutputStream
        try {
            emailService.sendEmailSalesReceipts(bytes2, salesReceiptDTO)
            return "Enviado correctamente al cliente ${salesReceiptDTO.client.email}"
        } catch (Exception ex) {
            ex.printStackTrace()
            return "Error al enviar ${ex.getMessage()}"
        }
    }
}