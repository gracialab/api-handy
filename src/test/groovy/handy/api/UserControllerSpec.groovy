package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import org.springframework.http.HttpStatus

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController> {

    def userService = Mock(UserService)

    def setup() {
        controller.userService = userService
    }

    def "test save with valid data"() {
        given:
        request.method = 'POST'
        request.contentType = "application/json"
        request.JSON = [name: "John", email: "john@example.com", phone: "123456789"]

        when:
        controller.save()

        then:
        1 * userService.saveUser(_ as User) >> { User user -> user.id = 1L }
        response.status == HttpStatus.CREATED.value()
        response.json.id == 1
        response.json.name == "John"
    }

    def "test show existing user"() {
        given:
        def user = new User(id: 1L, name: "John", email: "john@example.com", phone: "123456789")
        user.save(flush: true)

        when:
        controller.show(1L)

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == "John"
    }

    def "test update existing user"() {
        given:
        def user = new User(id: 1L, name: "John", email: "john@example.com", phone: "123456789")
        user.save(flush: true)

        request.method = 'PUT'
        request.contentType = "application/json"
        request.JSON = [name: "John Updated"]

        when:
        controller.update(1L)

        then:
        1 * userService.updateUser(_ as User)
        response.status == HttpStatus.OK.value()
        response.json.name == "John Updated"
    }

    def "test searchUsers with valid criteria"() {
        given:
        def criteria = new UserSearchCriteria(name: "John")
        def mockUsers = [new User(name: "John", email: "john@example.com", phone: "123456789")]

        when:
        controller.searchUsers(criteria)

        then:
        1 * userService.searchUsers(_ as UserSearchCriteria) >> mockUsers
        response.status == HttpStatus.OK.value()
        response.json[0].name == "John"
    }

    def "test deactivate user"() {
        when:
        controller.deactivate(1L)

        then:
        1 * userService.deactivateUser(1L)
        response.status == HttpStatus.OK.value()
        response.text == "Usuario desactivado correctamente"
    }

    def "test delete user"() {
        when:
        controller.delete(1L)

        then:
        1 * userService.deleteUser(1L)
        response.status == HttpStatus.OK.value()
        response.text == "Usuario eliminado correctamente"
    }
}
