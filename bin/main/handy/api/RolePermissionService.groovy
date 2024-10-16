package handy.api

import grails.gorm.transactions.Transactional

@Transactional
class RolePermissionService {

    def addPermissionToRole(String roleName, List<String> permissionNames){
        if (!roleName) {
            throw new IllegalArgumentException("El nombre del Rol es requerido.")
        }

        if (!permissionNames) {
            throw new IllegalArgumentException("El nombre de los permisos es requerido.")
        }
        def role = Role.findByNameIlike(roleName)
        if (!role) {
            throw new IllegalArgumentException("El rol '${roleName}' no existe.")
        }

        def existingPermissions = role.permissions*.name

        permissionNames.each {permissionName ->
            if(existingPermissions.contains(permissionName)){
                throw new IllegalArgumentException("El rol ya tiene asignado el permiso '${permissionName}'.")
            }
            def permission = Permission.findByNameIlike(permissionName)
            if (!permission) {
                throw new IllegalArgumentException("El permiso '${permissionNames}' no existe.")
            }

            role.addToPermissions(permission)
        }

        role.save(flush: true)

        return role
    }
}