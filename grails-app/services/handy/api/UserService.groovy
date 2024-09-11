package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class UserService {

    // Método para guardar al usuario
    def saveUser(User user) {
        // Valida el usuario antes de guardarlo
        if (!user.validate()) {
            throw new ValidationException("Datos inválidos: ${user.errors.allErrors.collect { it.defaultMessage }.join(', ')}", user.errors)
        }
        
        // Guarda el usuario en la base de datos
        if (user.save(flush: true)) {
            // Devuelve el usuario guardado
            return user
        } else {
            throw new ValidationException("Error al guardar el usuario", user.errors)
        }
    }

    // Método para actualizar el usuario
    def updateUser(User user) {
        // Valida el usuario antes de actualizarlo
        if (!user.validate()) {
            throw new ValidationException("Datos inválidos: ${user.errors.allErrors.collect { it.defaultMessage }.join(', ')}", user.errors)
        }

        // Guarda los cambios en la base de datos
        if (user.save(flush: true)) {
            // Devuelve el usuario actualizado
            return user
        } else {
            throw new ValidationException("Error al actualizar el usuario", user.errors)
        }
    }
}
