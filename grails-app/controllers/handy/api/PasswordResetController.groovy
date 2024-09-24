package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

@Transactional
class PasswordResetController {

    PasswordResetService passwordResetService

    def forgotPassword() {
        def jsonRequest = request.JSON

        try {
            passwordResetService.generatePasswordReset(jsonRequest.email)
            respond([message: "Por favor, revisa tu correo electrónico para restablecer tu contraseña"], status: HttpStatus.OK)
        } catch (IllegalArgumentException e) {
            respond([error: e.message], status: HttpStatus.BAD_REQUEST)
        } catch (RuntimeException e) {
            respond([error: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}