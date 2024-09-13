package handy.api

import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class UserController extends RestfulController<User> {
    static responseFormats = ['json']

    // Inyectamos el nuevo servicio UserService
    UserService userService

    UserController() {
        super(User)
    }

    // Método para guardar usuario utilizando el servicio UserService
    def save() {
        def jsonData = request.JSON
        println "Datos recibidos: ${jsonData}"

        // Validación de campos obligatorios
        if (!jsonData.name || !jsonData.email || !jsonData.phone) {
            render status: HttpStatus.BAD_REQUEST.value(), text: "Campos 'name', 'email' y 'phone' son obligatorios"
            return
        }

        // Crear una nueva instancia de User con los datos proporcionados
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
            // Usamos el servicio UserService para guardar al usuario
            userService.saveUser(user)
            respond user, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            def errorMessages = user.errors.allErrors.collect { it.defaultMessage }.join(', ')
            render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), text: "Datos inválidos: ${errorMessages}"
        } catch (Exception e) {
            def stackTrace = e.stackTrace.collect { "${it}" }.join('\n')
            render status: HttpStatus.INTERNAL_SERVER_ERROR.value(), text: "Error interno del servidor: ${e.message}\nStacktrace:\n${stackTrace}"
        }
    }

    // Método para mostrar un usuario por ID
    def show(Long id) {
        def user = User.findById(id)
        if (user) {
            respond user
        } else {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
        }
    }

    // Método para listar todos los usuarios
    def index() {
        def users = User.list()
        respond users
    }

    // Método para actualizar un usuario existente
    def update(Long id) {
        def jsonData = request.JSON
        println "Datos recibidos para actualizar: ${jsonData}"

        def user = User.findById(id)
        if (!user) {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
            return
        }

        // Actualizar solo los campos proporcionados en el JSON
        user.name = jsonData.name ?: user.name
        user.lastname = jsonData.lastname ?: user.lastname
        user.username = jsonData.username ?: user.username
        user.email = jsonData.email ?: user.email
        user.password = jsonData.password ?: user.password
        user.phone = jsonData.phone ?: user.phone
        user.address = jsonData.address ?: user.address
        user.preferences = jsonData.preferences ?: user.preferences
        user.updateAt = new Date()

        try {
            // Usamos el servicio UserService para actualizar al usuario
            userService.updateUser(user)
            respond user, status: HttpStatus.OK
        } catch (ValidationException e) {
            def errorMessages = user.errors.allErrors.collect { it.defaultMessage }.join(', ')
            render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), text: "Datos inválidos: ${errorMessages}"
        } catch (Exception e) {
            def stackTrace = e.stackTrace.collect { "${it}" }.join('\n')
            render status: HttpStatus.INTERNAL_SERVER_ERROR.value(), text: "Error interno del servidor: ${e.message}\nStacktrace:\n${stackTrace}"
        }
    }

    def deactivate(Long id) {
        userService.deactivateUser(id)
        render(status: 200, text: "Usuario desactivado correctamente")
    }

    def delete(Long id) {
        userService.deleteUser(id)
        render(status: 200, text: "Usuario eliminado correctamente")
    }
}
