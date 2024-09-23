package handy.api

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User>{

    User createUser(Map overrides = [:]) {
        return new User(
                name: overrides.name ?: "Andrés",
                lastname: overrides.lastname ?: "Diaz",
                username: overrides.username ?: "andres24",
                email: overrides.containsKey('email') ? overrides.email : "andres24@email.com",
                password: overrides.password ?: "Password123!",
                phone: overrides.phone ?: "1234567890",
                address: overrides.address ?: "Ipiales - Nariño",
                is_register: overrides.is_register ?: false
        )
    }

    void "test domain constraints"() {
        when: "A valid user is created"
        User domain = createUser()
        then: "The user must have all his fields validated"
        domain.validate()
    }

    void "Test for already registered email"(){
        given: "A user with already registered email"
        def existingUser = createUser(
                email: "john.doe@example.com"
        )
        existingUser.save(flush: true)

        when: "Another user is created with an  already existing email"
        def newUser = createUser(
                email: "john.doe@example.com"
        )

        then: "Validation fails because the email is already registered"
        !newUser.validate()
        newUser.errors['email'].codes.any { it.contains('unique')}
    }

    void "Test for unregistered email"(){
        when: "A user is created with a new email"
        def user = createUser(
                email: "john.doe@example.com"
        )

        then: "The user is valid"
        user.validate()
        !user.hasErrors()
    }

    void "Test with null entry in the email"(){
        when: "A user is created with null email"
        def user = createUser(email: null)
        then: "Validation fails because the field is null"
        assert !user.validate()
        user.errors['email'].codes.any { it.contains('nullable')}
    }


    void "Test with invalid email format"(){
        when: "A user is created with an invalid email"
        def user = createUser(
                email: "john"
        )
        then: "Validation should fail due to invalid email format"
        !user.validate()
        user.errors['email'].codes.any { it.contains('email.invalid') }
    }

    void "Test for valid password"(){
        when: "A user is created with a valid password"
        def user = createUser(
                password: "Password123.",
                is_register: true
        )
        then: "The user is valid"
        user.validate()
        !user.hasErrors()
    }

    void "Test for a password without a capital letter"(){
        when: "A user is crated with a password without a capital letter"
        def user = createUser(
                password: "password123@",
                is_register: true
        )
        then: "Validation fails for password without capital letter"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword') }

    }

    void "Test for password without number"(){
        when: "A user is created with a password without a number"
        def user = createUser(
                password: "Password@",
                is_register: true
        )
        then: "Validation fails because the password does not have a number"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }

    void "Test for a password without special character"(){
        when: "A user is created with a password without a special character"

        def user = createUser(
                password: "Password123",
                is_register: true
        )
        then: "Validation fails due to password without a special character"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }

    void "Test for password with less than 8 character"(){
        when: "A user is created with a password with less than 8 characters"
        def user = createUser(
                password: "Pass12@",
                is_register: true
        )
        then: "Validation fails for password less than 8 characters"
        !user.validate()
        user.errors['password'].codes.any { it.contains('invalidPassword')}
    }
}
