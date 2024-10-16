package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class RoleService {

    def saveRole(Role role){
        String formattedRole = role.name.toUpperCase().startsWith("ROLE_") ? role.name.toUpperCase(): "ROLE_"+role.name.toUpperCase()
        role.name = formattedRole

        if(!role.validate()){
            throw new ValidationException("No se puede guardar el Rol", role.errors)
        }

        def defaultPermission = Permission.findByNameIlike("Leer Tareas")
        if(!defaultPermission){
            throw new IllegalArgumentException("El permiso predeterminado no existe")
        }

        role.addToPermissions(defaultPermission)

        role.save(flush: true)
        return role
    }

    def listRoles(){
        def roles = Role.findAll()
        def rolesWithPermissions = roles.collect{role ->
            [
                    id: role.id,
                    name: role.name,
                    description: role.description,
                    permissions: role.permissions*.name
            ]
        }
        return rolesWithPermissions
    }

    def updateRole(int id, String newName, String newDescription){
        def role = Role.findById(id)

        if(!role){
            throw new IllegalArgumentException("Rol no encontrado")
        }

        role.name = newName
        role.description = newDescription

        if(!role.validate()){
            throw new ValidationException("Error", role.errors)
        }

        role.name = role.name.toUpperCase().startsWith("ROLE_") ? role.name.toUpperCase(): "ROLE_"+role.name.toUpperCase()

        role.save()
        return role
    }

    def deleteRole(int id){
        def role = Role.findById(id)

        if(!role){
            throw new IllegalArgumentException("Rol no encontrado")
        }

        def verifiedUserWithRole = User.createCriteria().list {
            eq("verified", true)
            roles {
                eq("id", role.id)
            }
        }

        if(verifiedUserWithRole.size()>0){
            throw new IllegalStateException("No se puede eliminar el rol porque está asignado a usuarios activos.")
        }

        role.delete(flush: true)
        return "Rol eliminado Correctamente"
    }
}