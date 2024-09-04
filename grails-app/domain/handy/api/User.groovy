package handy.api

class User {
    Long identification
    String name
    String lastname
    String username
    String phone
    String password
    String address
    String email
    Date createAt
    Date updateAt

    static mapping = {
        id name: 'identification'
        version false
        createAt column: 'create_at'
        updateAt column: 'update_at'
    }

    static constraints = {
        name nullable: false
        email nullable: false, email: true
        username nullable: false
        password nullable: false
        address nullable: true
        phone nullable: true
        lastname nullable: true
        createAt nullable: true
        updateAt nullable: true
    }
}
