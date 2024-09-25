package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

import java.time.Instant

@Transactional
class PasswordResetService {

    TokenService tokenService
    def springSecurityService
    EmailService emailService

    def generatePasswordReset(String email){
        User user = User.findByEmail(email)

        if (!user) {
            throw new IllegalArgumentException("El usuario no existe")
        }

        if(!user.verified){
            throw new RuntimeException("Cuenta no verificada")
        }

        String token = tokenService.generateToken(user)
        user.verification_token = token
        user.token_expiration = Instant.now().plusSeconds(3600)
        user.save(flush: true)

        emailService.sendPasswordResetEmail(email, token)
    }

    def resetPassword(String token, String newPassword){
        User user = User.findByVerification_token(token)
        if (!user || user.token_expiration.isBefore(Instant.now())) {
            throw new TokenExpiredEx("El token ha expirado")
        }
        user.is_register = true

        user.password = newPassword

        if (!user.validate()) {
            throw new ValidationException("Error Validation",user.errors)
        }
        user.password = springSecurityService.encodePassword(newPassword)
        user.verification_token = null
        user.token_expiration = null
        user.save(flush: true)
    }
}