package handy.api

import grails.gorm.transactions.Transactional

@Transactional
class RolePermissionService {

    def addPermissionToRole(String roleName, String permissionName){
        def role = Role.findByNameIlike(roleName)
        if (!role) {
            throw new IllegalArgumentException("El rol '${roleName}' no existe.")
        }

        def permission = Permission.findByNameIlike(permissionName)
        if (!permission) {
            throw new IllegalArgumentException("El permiso '${permissionName}' no existe.")
        }

        if(role.permissions.contains(permission)){
            throw new IllegalArgumentException("El rol ya tiene asignado el permiso '${permissionName}'.")
        }

        role.addToPermissions(permission)
        role.save(flush: true)

        return role
    }
}