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
    
    static hasMany = [roles: Role]
    static belongsTo = Role

    static mapping = {
        roles joinTable: [name: 'role_user', key: 'user_id', column: 'role_id']
        table 't_user'
        id column: 'identification'
    }

    static constraints = {
        name blank: false
        lastname nullable: true
        username blank: false, unique: true
        email email: true, blank: false, unique: true
        password blank: false
        phone matches: "\\d{10}", blank: false
        address nullable: true
        preferences nullable: true
    }
}