package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

@Transactional
class RoleController{

    RoleService roleService
    RolePermissionService rolePermissionService

    def save(){
        def jsonRequest = request.JSON
        def role = new Role(jsonRequest)

      try {
          roleService.saveRole(role)

          respond role, status: HttpStatus.CREATED
      }catch (ValidationException e){
          respond e.errors, status: HttpStatus.UNPROCESSABLE_ENTITY
      }
    }

    def list(){
        def listRoles = roleService.listRoles()
        respond listRoles, status: HttpStatus.OK
    }

    def update(int id){
        def jsonRequest = request.JSON
        String newName = jsonRequest.name
        String newDescription = jsonRequest.description

        try {
            def result = roleService.updateRole(id, newName, newDescription)
            respond result, status: HttpStatus.OK
        }catch (IllegalArgumentException e){
            respond([messsage: e.message], status: HttpStatus.BAD_REQUEST)
        }catch (ValidationException e){
            respond e.errors, status: HttpStatus.BAD_REQUEST
        }
    }

    def delete(int id){
        try{
            def result = roleService.deleteRole(id)
            respond([message: result], status: HttpStatus.OK)
        }catch (IllegalArgumentException e){
            respond([messsage: e.message], status: HttpStatus.BAD_REQUEST)
        }catch (IllegalStateException e){
            respond([messsage: e.message], status: HttpStatus.CONFLICT)
        }
    }

    def addPermissionToRole(){
        def jsonRequest = request.JSON
        String roleName = jsonRequest.roleName
        List<String> permissionNames = jsonRequest.permissionName

        try{
            def role = rolePermissionService.addPermissionToRole(roleName, permissionNames)
            respond role, status: HttpStatus.OK
        }catch (IllegalArgumentException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}