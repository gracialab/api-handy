package handy.api

class Permission {

    String name
    String description
    String module

    static hasMany = [roles: Role]

    static mapping = {
        version false
        roles joinTable: [name: 'permission_role', key: 'permission_id', column: 'role_id']
    }

    static constraints = {
        name nullable: false, blank: false, unique: true
        description nullable: false, blank: false
        module nullable: false, blank: false
    }
}