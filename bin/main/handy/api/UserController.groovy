package handy.api

import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus
import handy.api.UserSearchCriteria

/**
 * Controlador REST para gestionar usuarios.
 * Maneja las operaciones CRUD y búsqueda avanzada de usuarios.
 */
class UserController extends RestfulController<User> {
    static responseFormats = ['json']

    // Inyectamos el servicio UserService
    UserService userService

    UserController() {
        super(User)
    }

    /**
     * Guarda un nuevo usuario utilizando los datos recibidos.
     */
    def save() {
        def jsonData = request.JSON
        println "Datos recibidos: ${jsonData}"

        // Validación de campos obligatorios
        if (!jsonData.name || !jsonData.email || !jsonData.phone) {
            render status: HttpStatus.BAD_REQUEST.value(), text: "Los campos 'name', 'email' y 'phone' son obligatorios"
            return
        }

        def user = new User(
            name: jsonData.name,
            lastname: jsonData.lastname ?: '',
            username: jsonData.username ?: '',
            email: jsonData.email,
            password: jsonData.password ?: '',
            phone: jsonData.phone,
            address: jsonData.address ?: '',
            preferences: jsonData.preferences ?: '',
            createAt: new Date(),
            updateAt: new Date()
        )

        try {
            userService.saveUser(user)
            respond user, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            renderValidationErrors(user)
        } catch (Exception e) {
            renderInternalServerError(e)
        }
    }

    /**
     * Muestra un usuario por ID.
     */
    def show(Long id) {
        def user = User.findById(id)
        if (user) {
            respond user
        } else {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
        }
    }

    /**
     * Lista todos los usuarios.
     */
    def index() {
        def users = User.list()
        respond users
    }

    /**
     * Actualiza un usuario existente por ID.
     */
    def update(Long id) {
        def jsonData = request.JSON
        println "Datos recibidos para actualizar: ${jsonData}"

        def user = User.findById(id)
        if (!user) {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
            return
        }

        updateUserFields(user, jsonData)

        try {
            userService.updateUser(user)
            respond user, status: HttpStatus.OK
        } catch (ValidationException e) {
            renderValidationErrors(user)
        } catch (Exception e) {
            renderInternalServerError(e)
        }
    }

    /**
     * Desactiva un usuario por ID.
     */
    def deactivate(Long id) {
        userService.deactivateUser(id)
        render(status: 200, text: "Usuario desactivado correctamente")
    }

    /**
     *  Elimina un usuario por ID con confirmación previa.
     */
    def delete(Long id) {
        def user = User.findById(id)
    
        if (!user) {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
            return
        }

        // Verificar si se ha recibido la confirmación de eliminación
        def confirm = params.confirm ?: request.JSON.confirm
        if (!confirm || confirm != 'yes') {
            // Si la confirmación no está presente o es diferente de 'yes', devolver advertencia
            render status: HttpStatus.CONFLICT.value(), 
                    text: "¿Está seguro de que desea eliminar este usuario? Esta acción no puede deshacerse. Confirme con 'yes'."
            return
        }

        try {
            userService.deleteUser(id)
            render(status: HttpStatus.OK, text: "Usuario eliminado correctamente")
        } catch (Exception e) {
            render status: HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                    text: "Error al eliminar el usuario: ${e.message}"
        }
    }

    /**
     * Búsqueda avanzada de usuarios según criterios.
     */
    def searchUsers(UserSearchCriteria criteria) {
        if (!isSearchCriteriaValid(criteria)) {
            render status: HttpStatus.BAD_REQUEST.value(), text: "Debe proporcionar al menos un criterio de búsqueda."
            return
        }

        def users = userService.searchUsers(criteria)
        if (users) {
            respond users
        } else {
            render status: HttpStatus.NOT_FOUND.value(), text: "No se encontraron usuarios que coincidan con los criterios de búsqueda."
        }
    }

    // Métodos auxiliares para actualizar campos y manejar errores
    private void updateUserFields(User user, def jsonData) {
        user.name = jsonData.name ?: user.name
        user.lastname = jsonData.lastname ?: user.lastname
        user.username = jsonData.username ?: user.username
        user.email = jsonData.email ?: user.email
        user.password = jsonData.password ?: user.password
        user.phone = jsonData.phone ?: user.phone
        user.address = jsonData.address ?: user.address
        user.preferences = jsonData.preferences ?: user.preferences
        user.updateAt = new Date()
    }

    private boolean isSearchCriteriaValid(UserSearchCriteria criteria) {
        return criteria && (criteria.name || criteria.email || 
                            criteria.minPurchaseAmount || criteria.maxPurchaseAmount || 
                            criteria.minPurchaseDate || criteria.maxPurchaseDate || 
                            criteria.minPurchaseCount || criteria.maxPurchaseCount)
    }

    private void renderValidationErrors(User user) {
        def errorMessages = user.errors.allErrors.collect { it.defaultMessage }.join(', ')
        render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), text: "Datos inválidos: ${errorMessages}"
    }

    private void renderInternalServerError(Exception e) {
        def stackTrace = e.stackTrace.collect { "${it}" }.join('\n')
        render status: HttpStatus.INTERNAL_SERVER_ERROR.value(), text: "Error interno del servidor: ${e.message}\nStacktrace:\n${stackTrace}"
    }
}