package handy.api

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import grails.validation.ValidationException
import spock.lang.Specification

class RoleServiceSpec extends Specification implements ServiceUnitTest<RoleService>, DomainUnitTest<Role>{

    def setup(){
        def permission = new Permission(name: "Leer Tareas", description: "read tasks").save(flush: true)
    }

    void "test save role success"(){
        given: "A new valid role"
        def role = new Role(name: "Cliente", description: "User role")

        when: "saveRole is called"
        service.saveRole(role)

        then: "Role is successfully saved with default permission"
        Role savedRole = Role.findByNameIlike("Cliente")
        savedRole != null
        savedRole.permissions*.name.contains("Leer Tareas")
    }

    void "test save role fails with validation error"(){
        given: "An invalid role"
        def role = new Role(name: null)

        when: "trying to save the role"
        service.saveRole(role)

        then: "A validation exception is thrown"
        thrown(ValidationException)
    }

    void "test listRoles"() {
        given: "Existing roles in the database"
        def role1 = new Role(name: "Role 1", description: "Description 1").save(flush: true)
        def role2 = new Role(name: "Role 2", description: "Description 2").save(flush: true)

        when: "listRoles are called"
        def roles = service.listRoles()

        then: "All roles are returned with their permission"
        roles.size() == 2
        roles*.name.contains("Role 1")
        roles*.name.contains("Role 2")
    }

    void "test updateRole success"() {
        given: "an existing role"
        def role = new Role(name: "Old Name", description: "Old Description").save(flush: true)

        when: "Name and description updated"
        service.updateRole(role.id as int, "New Name", "New Description")

        then: "Role is updated correctly"
        def updatedRole = Role.findById(role.id)
        updatedRole.name == "New Name"
        updatedRole.description == "New Description"
    }

    void "test updateRole fails when role not found"() {
        when: "Trying to update a role that does not exist"
        service.updateRole(999, "New Name", "New Description")

        then: "An exception is thrown indicating that the role was not found"
        thrown(IllegalArgumentException)
    }

    void "test deleteRole success"() {
        given: "A role that is not assigned to verified users"
        def role = new Role(name: "Role to delete", description: "Test Role").save(flush: true)

        when: "deleteRole is called"
        def result = service.deleteRole(role.id as int)

        then: "Role is deleted successfully"
        result == "Rol eliminado Correctamente"
        !Role.findById(role.id)
    }

    void "test deleteRole fails if role assigned to verified user"() {
        given: "A role assigned to a verified user"
        def role = new Role(name: "Assigned Role", description: "admin").save(flush: true)
        def user = new User(verified: true).addToRoles(role).save(flush: true)

        when: "Trying to delete the role"
        service.deleteRole(role.id as int)

        then: "A state exception is thrown"
        thrown(IllegalStateException)
    }
}
