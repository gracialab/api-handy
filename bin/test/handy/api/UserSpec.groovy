package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User>{

    User createUser(Map overrides = [:]) {
        return new User(
                name: overrides.name ?: "John",
                lastname: overrides.lastname ?: "Doe",
                username: overrides.username ?: "johndoe",
                email: overrides.containsKey('email') ? overrides.email : "johndoe@example.com",
                password: overrides.password ?: "Password123!",
                phone: overrides.phone ?: "1234567890",
                address: overrides.address ?: "123 Main St, Anytown, USA",
                is_register: overrides.is_register ?: false
        )
    }

    void "test domain constraints"() {
        when: "Se crea un usario valido"
        User domain = createUser()
        then: "El usuario debe ser validado todos sus campos"
        domain.validate()
    }

    void "Prueba para correo ya registrado"(){
        given: "Un usuario con correo ya registrado"
        def existingUser = createUser(
                email: "john.doe@example.com"
        )
        existingUser.save(flush: true)

        when: "Se intenta crear otro usuario con un correo ya existente"
        def newUser = createUser(
                email: "john.doe@example.com"
        )

        then: "La validación falla porque el correo ya está registrado"
        !newUser.validate()
        newUser.errors['email'].codes.any { it.contains('unique')}
    }

    void "Prueba para correo no registrado"(){
        when: "Un usuario es creado con un nuevo email"
        def user = createUser(
                email: "john.doe@example.com"
        )

        then: "El usuario es validado"
        user.validate()
        !user.hasErrors()
    }

    void "Prueba con entrada nula en el correo"(){
        when: "Un usuario es creado con correo nulo"
        def user = createUser(email: null)
        then: "La validación falla porque el campo esta nulo"
        assert !user.validate()
        user.errors['email'].codes.any { it.contains('nullable')}
    }


    void "Prueba con formato de correo invalido"(){
        when: "Un usuario es creado con un correo invalido"
        def user = createUser(
                email: "john"
        )
        then: "La validación debe fallar por el formato de correo invalido"
        !user.validate()
        user.errors['email'].codes.any { it.contains('email.invalid') }
    }

    void "Prueba para contraseña valida"(){
        when: "Un usuario es creado con una contraseña valida"
        def user = createUser(
                password: "Password123.",
                is_register: true
        )
        then: "El usuario es valido"
        user.validate()
        !user.hasErrors()
    }

    void "Prueba para una contraseña sin mayuscula"(){
        when: "Un usuario es creado con una contraseña sin mayuscula"
        def user = createUser(
                password: "password123@",
                is_register: true
        )
        then: "La validación falla por contraseña sin mayuscula"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword') }

    }

    void "Prueba para contraseña sin numero"(){
        when: "Un usuario es creado con una contraseña sin numero"
        def user = createUser(
                password: "Password@",
                is_register: true
        )
        then: "La validación falla porque la contraseña no tiene numero"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }

    void "Prueba para una contraseña sin caracter especial"(){
        when: "Un usuario es creado con una contraseña sin caracter especial"

        def user = createUser(
                password: "Password123",
                is_register: true
        )
        then: "La validación falla por la contraseña sin un caracter especial"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }

    void "Prueba para contraseña con menos de 8 caracteres"(){
        when: "Un usario es creado con una contraseña con menos de 8 caracteres"
        def user = createUser(
                password: "Pass12@",
                is_register: true
        )
        then: "La validación falla por contraseña menos de 8 caracteres"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }
}
