package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

@Transactional
class PermissionController {

    PermissionService permissionService

    def save() {
        def json = request.JSON
        def permission = new Permission(json)

        try {
            def result = permissionService.savePermission(permission)
            respond result, status: HttpStatus.CREATED
        }catch (ValidationException e){
            respond(e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }
}