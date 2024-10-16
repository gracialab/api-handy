package handy.api

import grails.gorm.transactions.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit


class LoginService {

    def springSecurityService
    TokenService tokenService

    Map<String, Map> loginAttemptTracker = [:]
    def minutes = 0

    @Transactional
    def login(String email, String password){
        User user = User.findByEmail(email)

        if (!user) {
            log.warn("User not found with email: ${email}")
            throw new RuntimeException("Correo electrónico no encontrado")
        }

        if (!user.verified){
            throw new RuntimeException("La cuenta no está verificada.")
        }

        if(isAccountLocked(email)){
            throw new RuntimeException("La cuenta está temporalmente bloqueada. Intente nuevamente después de ${minutes} minutos.")
        }

        if (!springSecurityService.passwordEncoder.matches(password, user.password)) {
            handleFailedLoginAttempt(email)
            throw new RuntimeException("Contraseña incorrecta.")
        }

        resetFailedLoginAttempts(email)

        return tokenService.generateToken(user)
    }

    def handleFailedLoginAttempt(String email){
        if(!loginAttemptTracker.containsKey(email)){
            loginAttemptTracker[email] = [failedAttempts: 0, lockTime: null]
        }

        def userAttempts = loginAttemptTracker[email]
        userAttempts.failedAttempts++

        if(userAttempts.failedAttempts >= 5){
            userAttempts.lockTime = Instant.now()
        }
    }

    boolean isAccountLocked(String email){
        def userAttempts = loginAttemptTracker[email]
        minutes = 10

        if(userAttempts?.lockTime) {
            Instant lockTime = userAttempts.lockTime
            Instant now = Instant.now()

            if (ChronoUnit.MINUTES.between(lockTime, now) >= minutes) {
                resetFailedLoginAttempts(email)
                return false
            } else {
                return true
            }
        }
            return false
    }

    def resetFailedLoginAttempts(String email){
        loginAttemptTracker.remove(email)
    }
}