package handy.api

import grails.gorm.transactions.Transactional

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
}