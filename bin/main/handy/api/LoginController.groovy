package handy.api

import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

class LoginController{

    LoginService loginService

    @Transactional
    def auth(){
        def jsonRequest = request.JSON
        try {
            String token = loginService.login(jsonRequest.email, jsonRequest.password)
            respond([token: token], status: HttpStatus.OK)
        } catch (RuntimeException e){
            respond([error: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}