package handy.api

import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class UserRegistrationController extends RestfulController<User>{

    UserRegistrationService userRegistrationService

    UserRegistrationController() {
        super(User)
    }

    def register() {
        def jsonRequest = request.JSON
        def user = new User(jsonRequest as Map)

        try {
            userRegistrationService.registerUser(user)
            respond user, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            respond e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY
        } catch (IllegalArgumentException e) {
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}