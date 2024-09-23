package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class UserRegistrationService {

    def registerUser(User user) {
        user.is_register = true
        if (!user.validate()) {
            throw new ValidationException("No se puede guardar el usuario", user.errors)
        }
        Role role = Role.findByNameIlike("cliente")
        if (!role) {
            throw new IllegalArgumentException("El rol ${role} no existe en la base de datos")
        }

        user.addToRoles(role)

        user.save(flush: true)
        return user
    }
}