package handy.api

class Role {

    String name
    String description

    static hasMany = [users: User]

    static constraints = {
    }
}