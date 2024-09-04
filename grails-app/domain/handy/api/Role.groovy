package handy.api

class Role {

    String name
    String description

    static hasMany = [users: User]

    static mapping = {
        users joinTable: [name: 'role_user', key: 'role_id', column: 'user_id']
    }

    static constraints = {
    }
}