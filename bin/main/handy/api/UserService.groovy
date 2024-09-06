package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Transactional
class UserService {

    def saveUser(Map jsonData) {
        def user = new User(
            name: jsonData.name ?: '',
            lastname: jsonData.lastname ?: '',
            username: jsonData.username,
            phone: jsonData.phone ?: '',
            password: jsonData.password,
            address: jsonData.address ?: '',
            email: jsonData.email,
            createAt: new Date(),
            updateAt: new Date()
        )

        if (!user.save(flush: true)) {
            throw new ValidationException("Invalid User", user.errors)
        }

        return user
    }

    def updateUser(Long id, Map jsonData) {
        def user = User.findById(id)
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

        user.properties = jsonData
        user.updateAt = new Date()

        if (!user.save(flush: true)) {
            throw new ValidationException("Invalid User", user.errors)
        }

        return user
    }

    def deleteUser(Long id) {
        def user = User.findById(id)
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

        user.delete(flush: true)
    }
}
