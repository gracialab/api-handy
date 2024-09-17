package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import org.springframework.http.HttpStatus

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController> {

    def setup() {
        controller.userService = Mock(UserService) // Mock del servicio
    }

    def cleanup() {
        // Limpieza después de cada prueba (si es necesario)
    }

    // Prueba para el método save con datos válidos
    void "test save user with valid data"() {
        given:
        def userData = [
            name     : "John",
            email    : "john@example.com",
            phone    : "1234567890"
        ]

        controller.userService.saveUser(_) >> { User user ->
            user.id = 1L
            return user
        }

        when:
        request.method = 'POST'
        request.json = userData
        controller.save()

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == "John"
        response.json.email == "john@example.com"
    }

    // Prueba para el método show cuando el usuario existe
    void "test show user with valid id"() {
        given:
        def user = new User(id: 1, name: "John", email: "john@example.com")
        User.metaClass.static.findById = { Long id -> user }

        when:
        controller.show(1)

        then:
        response.status == 200
        response.json.name == "John"
        response.json.email == "john@example.com"
    }

    // Prueba para el método show cuando el usuario no existe
    void "test show user with invalid id"() {
        given:
        User.metaClass.static.findById = { Long id -> null }

        when:
        controller.show(999)

        then:
        response.status == HttpStatus.NOT_FOUND.value()
        response.text == "Usuario no encontrado"
    }

    // Prueba para el método delete
    void "test delete user"() {
        given:
        controller.userService.deleteUser(1L) >> {}

        when:
        controller.delete(1)

        then:
        response.status == HttpStatus.OK.value()
        response.text == "Usuario eliminado correctamente"
    }

    // Prueba para el método searchUsers con criterios válidos
    void "test search users with valid criteria"() {
        given:
        def criteria = new UserSearchCriteria(name: "John")
        controller.userService.searchUsers(criteria) >> [new User(name: "John")]

        when:
        controller.searchUsers(criteria)

        then:
        response.status == 200
        response.json.size() == 1
        response.json[0].name == "John"
    }
}
