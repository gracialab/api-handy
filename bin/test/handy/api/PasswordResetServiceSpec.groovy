package handy.api

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.Instant

class PasswordResetServiceSpec extends Specification implements ServiceUnitTest<PasswordResetService>, DomainUnitTest<User> {

    void "test generatePasswordReset throws IllegalArgumentException for non-existent user"() {
        given:
        def email = "nonexistent@example.com"
        User.metaClass.'static'.findByEmail = { String e -> null }  // Mock de la búsqueda del usuario

        when:
        service.generatePasswordReset(email)

        then:
        thrown(IllegalArgumentException)
    }

    void "test generatePasswordReset throws RuntimeException if user is not verified"() {
        given:
        def user = new User(email: "test@example.com", verified: false)
        User.metaClass.'static'.findByEmail = { String e -> user }

        when:
        service.generatePasswordReset(user.email)

        then:
        thrown(RuntimeException)
    }

    void "test resetPassword throws TokenExpiredEx if token is expired"() {
        given:
        def user = new User(verification_token: "valid-token", token_expiration: Instant.now().minusSeconds(3600))
        User.metaClass.'static'.findByVerification_token = { String t -> user }

        when:
        service.resetPassword("valid-token", "newPassword")

        then:
        thrown(TokenExpiredEx)
    }
}
