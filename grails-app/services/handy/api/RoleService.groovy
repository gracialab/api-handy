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

        role.save(flush: true)
        return role
    }

    def deleteRole(int id){
        def role = Role.findById(id)

        if(!role){
            throw new IllegalArgumentException("Rol no encontrado")
        }

        role.delete(flush: true)
        return "Rol eliminado Correctamente"
    }
}