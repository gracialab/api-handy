package handy.api

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

class LoginServiceSpec extends Specification implements ServiceUnitTest<LoginService>, DomainUnitTest<User> {


    void "test login throws exception when email not found"() {
        given:
        def email = "nonexistent@example.com"
        User.findByEmail(_) >> null  // Simula que el usuario no existe

        when:
        service.login(email, "somePassword")

        then:
        thrown(RuntimeException)
        0 * service.tokenService.generateToken(_)  // Verifica que no se ha generado ningún token
    }

    void "test login throws exception when account is not verified"() {
        given:
        def email = "user@example.com"
        def user = Mock(User) {
            getVerified() >> false
        }
        User.findByEmail(_) >> user  // Simula un usuario no verificado

        when:
        service.login(email, "somePassword")

        then:
        thrown(RuntimeException)  // Verifica que se lanza una excepción por cuenta no verificada
    }

    void "test login throws exception when password is incorrect"() {
        given:
        def email = "user@example.com"
        def user = Mock(User) {
            getVerified() >> true
        }
        User.findByEmail(_) >> user
//        springSecurityService.passwordEncoder.matches(_, _) >> false  // Simula una contraseña incorrecta

        when:
        service.login(email, "wrongPassword")

        then:
        thrown(RuntimeException)  // Verifica que se lanza una excepción por contraseña incorrecta
    }

    void "test login throws exception when account is locked"() {
        given:
        def email = "user@example.com"
        service.loginAttemptTracker[email] = [failedAttempts: 5, lockTime: Instant.now()]
        def user = Mock(User) {
            getVerified() >> true
        }
        User.findByEmail(_) >> user

        when:
        service.login(email, "correctPassword")

        then:
        thrown(RuntimeException)  // Verifica que se lanza una excepción por cuenta bloqueada
    }

    void "test account remains locked within 10 minutes"() {
        given:
        def email = "user@example.com"
        service.loginAttemptTracker[email] = [failedAttempts: 5, lockTime: Instant.now().minus(5, ChronoUnit.MINUTES)]

        when:
        def isLocked = service.isAccountLocked(email)

        then:
        isLocked == true  // Verifica que la cuenta sigue bloqueada
    }

}
