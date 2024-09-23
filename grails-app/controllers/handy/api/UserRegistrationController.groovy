package handy.api

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class UserRegistrationController extends RestfulController<User> {

    UserRegistrationService userRegistrationService
    TokenService tokenService
    EmailService emailService

    UserRegistrationController() {
        super(User)
    }

    @Transactional
    def register() {
        def jsonRequest = request.JSON
        def user = new User(jsonRequest)

        try {
            userRegistrationService.registerUser(user)
            emailService.sendVerificationEmail(user.email, user.getVerification_token())

            respond([message: "Registro exitoso. Por favor, revisa tu correo electrónico para verificar tu cuenta."], status: HttpStatus.CREATED)
        } catch (ValidationException e) {
            respond e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY
        } catch (IllegalArgumentException e) {
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }

    @Transactional
    def verifyAccount() {
        String token = params.token
        try {
            def user = tokenService.verifyToken(token)
            user.verified = true
            user.save(flush: true, failOnError: true)
            render status: 200, text: "Cuenta verificada exitosamente."
        } catch (TokenExpiredEx e) {
            log.error(e.message)
            render(status: 404, text: 'El token ha expirado. Se ha enviado un nuevo enlace de verificación a tu correo electrónico.')
        } catch (InvalidTokenException e) {
            log.error(e.message)
            render(status: 400, text: 'La cuenta ya está activa o el enlace de verificación es inválido.')
        } catch (Exception e) {
            log.error("Error al verificar la cuenta ${e.message}")
            render(status: 500, text: 'Ocurrió un error al verificar la cuenta.')
        }
    }
}