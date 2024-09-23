package handy.api

import grails.gorm.transactions.Transactional


@Transactional
class PermissionService {


    Permission savePermission(String name, String description) {
        def permission = new Permission(name: name, description: description)
        if (!permission.save(flush: true)) {
            // Manejo de errores
            permission.errors.allErrors.each {
                println it
            }
        }
        return permission
    }
}