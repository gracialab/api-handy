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

    def resetPassword() {
        def jsonRequest = request.JSON

        try {
            String token = params.token
            String newPassword = jsonRequest.password

            passwordResetService.resetPassword(token, newPassword)
            respond([message: "La contraseña ha sido restablecida exitosamente."], status: HttpStatus.OK)
        } catch (TokenExpiredEx ex) {
            respond([message: ex.message], status: HttpStatus.BAD_REQUEST)
        } catch (ValidationException e) {
            respond e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY
        } catch (Exception e) {
            respond([error: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}