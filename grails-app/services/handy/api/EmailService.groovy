package handy.api

import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService

import java.time.format.DateTimeFormatter

@Transactional
class EmailService {

    MailService mailService
    def grailsApplication

    def sendVerificationEmail(String email, String token){

        String baseUrl = grailsApplication.config.grails.serverURL
        String verificationLink = "${baseUrl}/verifyAccount?token=${token}"

        mailService.sendMail {
            to email
            subject "Verifica tu cuenta"
            html "<p>Por favor, verifica tu cuenta haciendo clic en el siguiente enlace: <a href='${verificationLink}'>Verificar cuenta</a></p>"
        }
    }

    def sendPasswordResetEmail(String email, String token) {
        String baseUrl = grailsApplication.config.grails.serverURL
        String resetLink = "${baseUrl}/password/reset?token=${token}"

        mailService.sendMail {
            to email
            subject "Recupera tu contraseña"
            html "<p>Para restablecer tu contraseña, haz clic en el siguiente enlace: <a href='${resetLink}'>Restablecer contraseña</a></p>"
        }
    }

    def sendEmailSalesReceipts(ByteArrayOutputStream bytes, SalesReceiptDTO salesReceiptDTO) {
      try {
          File tempPdfFile = File.createTempFile("order_", ".pdf")
          tempPdfFile.withOutputStream { fileOutputStream ->
              fileOutputStream.write(bytes.toByteArray())}
          DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
          String fetchFormatted = salesReceiptDTO.dateOrder.format(format)
          mailService.sendMail {
              multipart true
              to salesReceiptDTO.client.email
              subject "Envio de factura - Orden de compra # ${salesReceiptDTO.order.idOrder}"
              body "Es un placer saludarte de parte de todo el equipo " + "te remitimos la [factura/orden de compra] correspondiente a tu reciente adquisición de productos en nuestra tienda."
              html """
                    <p>Estimado/a <strong>${salesReceiptDTO.client.name} ${salesReceiptDTO.client.lastname}  </strong>,</p>

                    <p>Adjunto te remitimos la <strong>factura</strong> correspondiente a tu reciente adquisición de productos en nuestra tienda.</p>            
                    <p><strong>Detalles de la transacción:</strong></p>
                    <ul>
                        <li><strong>Número de Factura:</strong> ${salesReceiptDTO.order.idOrder}</li>
                        <li><strong>Fecha de la transacción:</strong> ${fetchFormatted}</li>
                        <li><strong>Monto Total:</strong> ${salesReceiptDTO.total}</li>
                    </ul>
            
                    <p>Si necesitas más información o tienes alguna duda, no dudes en contactarnos.</p>
                    <p>Gracias por confiar en nosotros</p>
                """
              attach tempPdfFile.name, 'application/pdf', tempPdfFile.bytes // Attachment PDF
          }

          // Delete file temp
          tempPdfFile.delete()
      } catch (Exception ex) {
          ex.printStackTrace()
      }
    }

}