package handy.api

class Role {

    String name
    String description

    static hasMany = [users: User, permissions: Permission]
    static belongsTo = [Permission]

    static mapping = {
        version false
        users joinTable: [name: 'role_user', key: 'role_id', column: 'user_id']
        permissions joinTable: [name: 'permission_role', key: 'role_id', column: 'permission_id']
    }

    static constraints = {
        name blank: false, unique: true
        description blank: false
    }
}
