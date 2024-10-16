package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException


@Transactional
class PermissionService {

    def savePermission(Permission permission) {
        if(!permission.validate()){
            throw new ValidationException("Error", permission.errors)
        }

        permission.name = permission.name.toUpperCase()

        permission.save(flush: true)
        return permission
    }

    def listPermissions(){
        Permission.list()
    }

    def updatePermission(Long id, String permissionName, String description, String module) {
        def permission = Permission.findById(id)
        if (!permission) {
            throw new IllegalArgumentException("Permiso no encontrado")
        }

        permission.name = permissionName
        permission.description = description
        permission.module = module

        if (!permission.validate()) {
            throw new ValidationException("ERROR", permission.errors)
        }

        permission.name = permission.name.toUpperCase()

        permission.save(flush: true)
    }

    def deletePermission(Long id){
        def permission = Permission.findById(id)
        if(!permission){
            throw new IllegalArgumentException("Permiso no encontrado")
        }

        if(permission.roles.size() > 0){
            throw new IllegalStateException("No se puede eliminar el permiso, porque esta asignado a un rol o mas roles")
        }

        permission.delete(flush: true)
        return "Permiso Eliminado Correctamente"
    }
}