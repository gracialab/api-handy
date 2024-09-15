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
    Date updateAt
    boolean active = true 
    String purchaseHistory 
    
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
        email email: true, blank: false, unique: true // Valida que el correo tenga un formato correcto
        password blank: false
        phone matches: "\\d{10}", blank: false // Valida que el teléfono tenga 10 dígitos
        address nullable: true
        preferences nullable: true
        purchaseHistory nullable: true
    }
}