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
    String verification_token
    Boolean is_register = false
    Boolean verified = false

    static hasMany = [roles: Role]
    static belongsTo = Role

    static mapping = {
        roles joinTable: [name: 'role_user', key: 'user_id', column: 'role_id'], fetch: 'join'
        table 't_user'
        id column: 'identification'
    }

    static constraints = {
        name blank: false
        lastname blank: false
        username blank: false, unique: true
        email blank: false, nullable: false, email: true, unique: true
        phone matches: "\\d{10}", blank: false
        address nullable: true
        preferences nullable: true
        password nullable: true, blank: false, validator: { val, obj ->
            if (obj.is_register && (!val || !obj.validatePassword(val))) {
                return ['invalidPassword']
            }
        }
    }

    def validatePassword(String password){
        def passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/

        return password.matches(passwordPattern)
    }
}