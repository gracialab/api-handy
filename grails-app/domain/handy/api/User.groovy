package handy.api


class User {

    String name
    String lastname
    String username
    String email
    String password
    String phone
    String address
    String preferences
    String active = true

    static hasMany = [roles: Role]
    static belongsTo = Role

    static mapping = {
        roles joinTable: [name: 'role_user', key: 'user_id', column: 'role_id']
        table 'customer'
        id column: 'identification'
    }

    static constraints = {
        name blank: false
        email email: true, blank: false, unique: true
        phone matches: "\\d{10}", blank: false
        address nullable: true
        preferences nullable: true
    }
}