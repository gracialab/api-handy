package handy.api

class UserDTO {
    Long id
    String name
    String lastname
    String email
    String address

    UserDTO() {}

    UserDTO(Long id, String name, String lastname, String email, String address) {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.email = email
        this.address = address
    }
}