package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

@Transactional
class RoleController{

    RoleService roleService

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
}