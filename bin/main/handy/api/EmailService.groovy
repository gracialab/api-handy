package handy.api

import grails.gorm.transactions.Transactional
import grails.plugins.mail.MailService

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

}