package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import handy.api.UserSearchCriteria

/**
 * Servicio para gestionar operaciones relacionadas con los usuarios,
 * incluyendo creación, actualización, eliminación y búsqueda avanzada.
 */
@Transactional
class UserService {

    /**
     * Guarda un usuario en la base de datos.
     * 
     * @param user El objeto User a guardar.
     * @throws ValidationException Si los datos del usuario son inválidos.
     * @return El usuario guardado.
     */
    def saveUser(User user) {
        if (!user.validate()) {
            user.errors.each { println it }  
            throw new ValidationException("Datos inválidos: ${user.errors.allErrors.collect { it.defaultMessage }.join(', ')}", user.errors)
        }
        
        if (user.save(flush: true)) {
            return user
        } else {
            throw new ValidationException("Error al guardar el usuario", user.errors)
        }
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param user El objeto User a actualizar.
     * @throws ValidationException Si los datos del usuario son inválidos.
     * @return El usuario actualizado.
     */
    def updateUser(User user) {
        validateAndSave(user)
    }

    /**
     * Desactiva (soft delete) un usuario estableciendo el campo 'active' en false.
     * 
     * @param id El ID del usuario a desactivar.
     * @throws ValidationException Si ocurre un error al desactivar el usuario.
     * @throws IllegalArgumentException Si el usuario no existe.
     */
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

    /**
     * Elimina un usuario de forma permanente.
     * 
     * @param id El ID del usuario a eliminar.
     * @throws IllegalArgumentException Si el usuario no existe.
     */
    def deleteUser(Long id) {
        def user = User.findById(id)
        if (user) {
            user.delete(flush: true)
        } else {
            throw new IllegalArgumentException("Usuario no encontrado")
        }
    }

    /**
     * Realiza una búsqueda avanzada de usuarios basado en criterios específicos.
     * 
     * @param criteria Criterios de búsqueda encapsulados en un objeto UserSearchCriteria.
     * @return Lista de usuarios que coinciden con los criterios de búsqueda.
     */
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

    /**
     * Método privado para validar y guardar un usuario.
     * 
     * @param user El objeto User a validar y guardar.
     * @throws ValidationException Si los datos son inválidos.
     * @return El usuario guardado.
     */
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
