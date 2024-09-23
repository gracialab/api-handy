package handy.api

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class TokenService {

    EmailService emailService

    String jwtSecret = System.getenv('JWT_SECRET') ?: "default"

    String generateToken(User user) {
        def roles = user.roles*.name

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret)
            return JWT.create()
                    .withIssuer('handy')
                    .withSubject(user.name)
                    .withClaim("Roles", roles)
                    .sign(algorithm)
        } catch (JWTCreationException ex) {
            log.error("Error al crear el token JWT: ${ex.message}", ex)
        }
    }

    User verifyToken(String token) {
        User user = User.findByVerification_token(token)

        if (user.verified) {
            throw new InvalidTokenException("La cuenta ya está activa.")
        }

        if (user.token_expiration && user.token_expiration.isBefore(Instant.now())) {

            String newToken = generateToken(user)
            user.verification_token = newToken
            Instant expirationDate = Instant.now().plusSeconds(86400)
            user.token_expiration = expirationDate

            emailService.sendVerificationEmail(user.email, newToken)

            throw new TokenExpiredEx("El token ha expirado para el usuario: ${user.email}")
        }

        return user
    }

    Instant generateExpirationDate(int numberHours) {
        return LocalDateTime.now().plusHours(numberHours).toInstant(ZoneOffset.of("-05:00"))
    }
}

class TokenExpiredEx extends RuntimeException {
    TokenExpiredEx(String message) {
        super(message)
    }
}

class InvalidTokenException extends RuntimeException {
    InvalidTokenException(String message) {
        super(message)
    }
}