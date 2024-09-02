package handy.api

class User {
    String name
    String email
    String phone
    String address
    String preferences
    String active = true

    static constraints = {
        name blank: false
        email email: true, blank: false
        phone matches: "\\d{10}", blank: false
        address nullable: true
        preferences nullable: true
    }
}