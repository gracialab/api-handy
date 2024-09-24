package handy.api

import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import org.springframework.http.HttpStatus
import spock.lang.Specification

class UserRegistrationControllerSpec extends Specification implements ControllerUnitTest<UserRegistrationController> {

    def userRegistrationService = Mock(UserRegistrationService)

    void setup(){
        controller.userRegistrationService = userRegistrationService
    }

    Map createdUser() {
        return [
                name:  "Andrés",
                lastname: "Diaz",
                username: "andres24",
                email: "andres24@email.com",
                password: "Password123!",
                phone: "1234567890",
                address: "Ipiales - Nariño",
                is_register: true
        ]
    }

    def "Register a user with valid data"() {
        given: "Loads valid data for a user"
        def jsonRequest = createdUser()
        request.json = jsonRequest
        def user = new User(jsonRequest)

        when: "The user is registered"
        userRegistrationService.registerUser(user)
        controller.register()

        then: "The user is created successfully and is responded with HTTP status 201"
        response.status == HttpStatus.CREATED.value()
    }

    def "Returns an error if the email is already registered"() {
        given: "A user with a duplicate email"
        def jsonRequest = createdUser()
        request.json = jsonRequest
        def user = new User(jsonRequest)

        when: "The service tries to register a user with a duplicate email"
        userRegistrationService.registerUser(_) >> { throw new ValidationException("User email already exists", user.errors) }

        controller.register()

        then: "Responds with a validation error and HTTP status 422"
        response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
    }

    def "Should return error if required fields are absent"() {
        given: "A user with missing field"
        def jsonRequest = createdUser()
        jsonRequest.remove('username')
        jsonRequest.remove('email')
        jsonRequest.remove('password')

        request.json = jsonRequest

        when: "The controller tries to register the user"
        controller.register()

        then: "Responds with a validation error and HTTP status 422"
        1 * controller.userRegistrationService.registerUser(_) >> { throw new ValidationException("Missing required fields", new User(jsonRequest).errors) }
        response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
    }
}
