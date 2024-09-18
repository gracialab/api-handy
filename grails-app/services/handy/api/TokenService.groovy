package handy.api

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import groovy.transform.CompileStatic

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@CompileStatic
class TokenService {

   String jwtSecret = System.getenv('JWT_SECRET')?: 12

    String generateToken(User user) {
        def roles = user.roles*.name
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret)
            return JWT.create()
                    .withIssuer('handy')
                    .withSubject(user.name)
                    .withClaim("Role", roles)
                    .withExpiresAt(generateExpirationDate(2))
                    .sign(algorithm)
        }catch (JWTCreationException ex){
            ex.message
        }
    }

    Instant generateExpirationDate(int numberHours){
        return LocalDateTime.now().plusHours(numberHours).toInstant(ZoneOffset.of("-05:00"))
    }


}