package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

import java.time.Instant


class UserRegistrationService {

    def springSecurityService
    TokenService tokenService

    @Transactional
    def registerUser(User user) {
        user.is_register = true

        Role role = Role.findByNameIlike("cliente")
        if (!role) {
            throw new IllegalArgumentException("El rol ${role} no existe en la base de datos")
        }

        user.addToRoles(role)

        if (!user.validate()) {
            throw new ValidationException("No se puede guardar el usuario", user.errors)
        }
        String encodedPassword = springSecurityService.encodePassword(user.password)
        user.password = encodedPassword

        user.save(flush: true)

        String token = tokenService.generateToken(user)
        user.verification_token = token
        Instant expirationDate = Instant.now().plusSeconds(86400)
        user.token_expiration = expirationDate

        user.save(flush: true)
        return user
    }
}