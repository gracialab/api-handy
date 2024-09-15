package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import handy.api.UserSearchCriteria

@Transactional
class UserService {

    // Método para guardar al usuario
    def saveUser(User user) {
        // Valida el usuario antes de guardarlo
        if (!user.validate()) {
            // Imprime los errores de validación en la consola
            user.errors.each { println it }  
            
            // Lanza una excepción con los errores de validación
            throw new ValidationException("Datos inválidos: ${user.errors.allErrors.collect { it.defaultMessage }.join(', ')}", user.errors)
        }
        
        // Si la validación pasa, guarda el usuario
        if (user.save(flush: true)) {
            return user
        } else {
            // Si falla al guardar, lanza una excepción
            throw new ValidationException("Error al guardar el usuario", user.errors)
        }
    }


    // Método para actualizar el usuario
    def updateUser(User user) {
        validateAndSave(user)
    }

    // Método para desactivar un usuario (soft delete)
    def deactivateUser(Long id) {
        def user = User.findById(id)
        if (user) {
            user.active = false
            if (!user.save(flush: true)) {
                throw new ValidationException("Error al desactivar el usuario", user.errors)
            }
        } else {
            throw new IllegalArgumentException("Usuario no encontrado")
        }
    }

    // Método para eliminar permanentemente un usuario
    def deleteUser(Long id) {
        def user = User.findById(id)
        if (user) {
            user.delete(flush: true)
        } else {
            throw new IllegalArgumentException("Usuario no encontrado")
        }
    }

    // Método para la búsqueda avanzada de usuarios
    List<User> searchUsers(UserSearchCriteria criteria) {
        def query = User.createCriteria().list {
            if (criteria.name) {
                ilike('name', "%${criteria.name}%")
            }
            if (criteria.email) {
                ilike('email', "%${criteria.email}%")
            }
            if (criteria.minPurchaseAmount) {
                ge('totalPurchaseAmount', criteria.minPurchaseAmount)
            }
            if (criteria.maxPurchaseAmount) {
                le('totalPurchaseAmount', criteria.maxPurchaseAmount)
            }
            if (criteria.minPurchaseDate) {
                ge('lastPurchaseDate', criteria.minPurchaseDate)
            }
            if (criteria.maxPurchaseDate) {
                le('lastPurchaseDate', criteria.maxPurchaseDate)
            }
            if (criteria.minPurchaseCount) {
                ge('purchaseCount', criteria.minPurchaseCount)
            }
            if (criteria.maxPurchaseCount) {
                le('purchaseCount', criteria.maxPurchaseCount)
            }
        }

        return query
    }

    // Método privado para validar y guardar al usuario
    private validateAndSave(User user) {
        if (!user.validate()) {
            throw new ValidationException("Datos inválidos: ${user.errors.allErrors.collect { it.defaultMessage }.join(', ')}", user.errors)
        }

        if (!user.save(flush: true)) {
            throw new ValidationException("Error al guardar el usuario", user.errors)
        }

        return user
    }
}
