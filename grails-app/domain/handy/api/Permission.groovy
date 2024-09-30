package handy.api

class Permission {

    String name
    String description

    static hasMany = [roles: Role]

    static mapping = {
        version false
        roles joinTable: [name: 'permission_role', key: 'permission_id', column: 'role_id']
    }

    static constraints = {

    }
}