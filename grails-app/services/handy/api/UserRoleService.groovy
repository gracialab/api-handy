package handy.api

import grails.gorm.transactions.Transactional

@Transactional
class UserRoleService {

    def addRoleToUser(Long idUser, String roleName){
        if (!idUser) {
            throw new IllegalArgumentException("El ID del usuario es requerido.")
        }

        if (!roleName) {
            throw new IllegalArgumentException("El nombre del rol es requerido.")
        }
        def user = User.findById(idUser)
        if(!user){
            throw new IllegalArgumentException("No existe un usuario con el id: ${idUser}")
        }

        def role = Role.findByNameIlike(roleName)
        if(!role){
            throw new IllegalArgumentException("No existe el rol: ${roleName}")
        }

        user.addToRoles(role)
        user.save(flush: true)
        return user
    }

    def removeRoleFromUser(Long idUser, String roleName){
        if (!idUser) {
            throw new IllegalArgumentException("El ID del usuario es requerido.")
        }

        if (!roleName) {
            throw new IllegalArgumentException("El nombre del rol es requerido.")
        }

        def user = User.findById(idUser)
        if(!user){
            throw new IllegalArgumentException("No existe un usuario con el id: ${idUser}")
        }

        def role = Role.findByNameIlike(roleName)
        if(!role){
            throw new IllegalArgumentException("No existe el rol: ${roleName}")
        }

        if(!user.roles.contains(role)){
            throw new IllegalArgumentException("El usuario '${username}' no tiene asignado el rol '${roleName}'.")
        }

        user.removeFromRoles(role)
        user.save(flush: true)
        return user
    }
}