package handy.api

import grails.rest.RestfulController
import grails.converters.JSON
import org.springframework.transaction.annotation.Transactional

class UserController extends RestfulController<User> {
    static responseFormats = ['json']

    UserController() {
        super(User)
    }
    @Transactional
    def save() {
        // Leer los datos JSON de la solicitud
        def jsonData = request.JSON
        println "Datos recibidos: ${jsonData}"

        // Verificar que todos los campos obligatorios están presentes
        if (!jsonData.username || !jsonData.password) {
            render status: 400, text: "Campos 'username' y 'password' son obligatorios"
            return
        }

        // Crear una instancia de User con los datos JSON
        def user = new User(
            name: jsonData.name ?: '',
            lastname: jsonData.lastname ?: '',
            username: jsonData.username,
            phone: jsonData.phone ?: '',
            password: jsonData.password,
            address: jsonData.address ?: '',
            email: jsonData.email,
            createAt: new Date(), // Establece la fecha actual
            updateAt: new Date()  // Establece la fecha actual
        )

        if (!user.save(flush: true)) {
            // Manejo de errores
            render status: 400, text: "Datos inválidos: ${user.errors}"
            return
        }

        render status: 201, text: "Usuario creado exitosamente"
    }

    def update() {
        // Leer los datos JSON de la solicitud
        def jsonData = request.JSON
        Long id = jsonData.id

        def user = User.findByIdentification(id)
        if (user == null) {
            render status: 404, text: "Usuario no encontrado"
            return
        }

        // Actualizar el usuario con los nuevos datos
        user.properties = jsonData
        user.updateAt = new Date() // Actualiza la fecha de modificación

        if (!user.save(flush: true)) {
            // Manejo de errores
            render status: 400, text: "Datos inválidos: ${user.errors}"
            return
        }

        render status: 200, text: "Usuario actualizado exitosamente"
    }

    def delete(Long id) {
        def user = User.findByIdentification(id)
        if (user == null) {
            render status: 404, text: "Usuario no encontrado"
            return
        }

        user.delete(flush: true)
        render status: 200, text: "Usuario eliminado exitosamente"
    }
}
