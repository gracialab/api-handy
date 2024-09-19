package handy.api

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class UserRegistrationController extends RestfulController<User>{

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

            respond user, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            respond e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY
//            respond e.errors, [status: HttpStatus.UNPROCESSABLE_ENTITY, formats: ['application/json']]
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
                render(status: 404, text: 'El token ha expirado. Por favor, solicita un nuevo enlace de verificación.')
            }catch (Exception e){
                log.error("Error al verificar la cuenta ${e.message}")
                render(status: 500, text: 'Ocurrió un error al verificar la cuenta.')
            }
    }
}