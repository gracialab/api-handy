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

    def list(){
        def results = permissionService.listPermissions()
        respond results, status: HttpStatus.OK
    }

    def update(Long id){
        def requestJson = request.JSON
        String name = requestJson.name
        String description = requestJson.description
        String module = requestJson.module

        try {
            def result = permissionService.updatePermission(id, name, description, module)
            respond result, status: HttpStatus.OK
        }catch (IllegalArgumentException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }catch (ValidationException e){
            respond(e.errors, status: HttpStatus.BAD_REQUEST)
        }
    }

    def delete(Long id){
        try {
            def result = permissionService.deletePermission(id)
            respond([message: result], status: HttpStatus.OK)
        }catch (IllegalArgumentException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }catch (IllegalStateException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}