package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

class UserRegistrationService {

    def springSecurityService
    TokenService tokenService

    @Transactional
    def registerUser(User user) {
        user.is_register = true

        String encodedPassword = springSecurityService.encodePassword(user.password)
        user.password = encodedPassword

        Role role = Role.findByNameIlike("cliente")
        if (!role) {
            throw new IllegalArgumentException("El rol ${role} no existe en la base de datos")
        }

        user.addToRoles(role)

        String token = tokenService.generateToken(user)
        user.verification_token = token

        if (!user.validate()) {
            throw new ValidationException("No se puede guardar el usuario", user.errors)
        }

        user.save(flush: true)
        return user
    }
}