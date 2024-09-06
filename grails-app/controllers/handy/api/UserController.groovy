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

        // Validación de los campos requeridos
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

    // Mostrar usuario por ID
    def show(Long id) {
        def user = User.findById(id)
        if (user) {
            respond user
        } else {
            render status: HttpStatus.NOT_FOUND.value(), text: "Usuario no encontrado"
        }
    }

    // Listar todos los usuarios
    def index() {
        def users = User.list()
        respond users
    }
}
