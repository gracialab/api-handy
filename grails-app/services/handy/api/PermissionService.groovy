package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException


@Transactional
class PermissionService {


    def savePermission(Permission permission) {
        if(!permission.validate()){
            throw new ValidationException("Error", permission.errors)
        }
        permission.save(flush: true)
        return permission
    }

    def listPermissions(){
        Permission.list()
    }
}