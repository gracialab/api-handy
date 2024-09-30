package handy.api


import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import grails.validation.ValidationException
import spock.lang.Specification

class UserRegistrationServiceSpec extends Specification implements ServiceUnitTest<UserRegistrationService>, DomainUnitTest<User> {

    UserRegistrationService service = new UserRegistrationService()

 // Simula las clases de dominio
    void "Registro de usuario exitoso"() {
        given: "Un usario con atributos validos"
        def user = new User(
                name: "Andrés",
                lastname: "Diaz",
                username: "andres24",
                email: "andres24@gmail.com",
                password: "Password123!",
                phone: "1234567890",
                address: "Ipiales - Nariño"
        )

        and: "Un rol cliente existente"
        def role = Mock(Role)
        role.name >> "cliente"
        role.description >> "client"
        role.save(flush: true)

        when: "Se registra el usuario"
        def registeredUser = service.registerUser(user)

        then: "El usuario se registra correctamente"
        registeredUser.is_register == true
        registeredUser.roles.contains(role)
    }

    void "Debe fallar si la validación del usuario es incorrecta"() {
        given: "Un usuario con datos inválidos"
        def user = new User(email: "invalid-email")
        user.metaClass.validate = { false } // Simulamos que la validación falla

        when: "Intentamos registrar el usuario"
        service.registerUser(user)

        then: "Se lanza una ValidationException"
        thrown(ValidationException)
    }
}
