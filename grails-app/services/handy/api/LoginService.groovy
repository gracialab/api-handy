package handy.api

import grails.gorm.transactions.Transactional


class LoginService {

    def springSecurityService
    TokenService tokenService

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

        if (!springSecurityService.passwordEncoder.matches(password, user.password)) {
            throw new RuntimeException("Contraseña incorrecta.")
        }

        return tokenService.generateToken(user)
    }
}