package handy.api

import grails.gorm.transactions.Transactional
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UserRegistrationServiceSpec extends Specification implements ServiceUnitTest<UserRegistrationService> {

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

//        Role.metaClass.'static'.findByNameIlike = { String name ->
//            return name == "cliente" ? role : null
//        }

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

//    void "Debe lanzar excepción si el rol 'cliente' no existe"() {
//        given: "Un usuario válido"
//        def user = new User(
//                name: "John",
//                lastname: "Doe",
//                username: "johndoe",
//                email: "johndoe@example.com",
//                password: "Password123!"
//        )
//
//        and: "El rol 'cliente' no existe"
//        Role.metaClass.'static'.findByNameIlike = { "cliente" -> null }
//
//        when: "Se intenta registrar el usuario"
//        service.registerUser(user)
//
//        then: "Se lanza una IllegalArgumentException"
//        def e = thrown(IllegalArgumentException)
//        e.message == "El rol cliente no existe en la base de datos"
//    }

//    void "Debe guardar el usuario correctamente"() {
//        given: "Un usuario válido"
//        def user = Mock(User)
//        user.validate() >> true
//        user.addToRoles(_) >> user
//
//        and: "Un rol de cliente existente"
//        def role = Mock(Role)
//        Role.metaClass.'static'.findByNameIlike = { "cliente" -> role }
//
//        when: "Se registra el usuario"
//        service.registerUser(user)
//
//        then: "El usuario se guarda correctamente"
//        1 * user.save(_)
//    }

}
