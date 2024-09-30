package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class RoleService {

    def saveRole(Role role){
        if(!role.validate()){
            throw new ValidationException("No se puede guardar el Rol", role.errors)
        }

        role.save(flush: true)
        return role
    }

    def listRoles(){
        Role.list()
    }
}