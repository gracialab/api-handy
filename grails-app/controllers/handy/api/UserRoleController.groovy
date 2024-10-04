package handy.api

import grails.gorm.transactions.Transactional
import org.springframework.http.HttpStatus

@Transactional
class UserRoleController {

    UserRoleService userRoleService

    def addRoleToUser(){
        def jsonRequest = request.JSON
        Long id = jsonRequest.id
        String roleName = jsonRequest.roleName

        try {
            def result = userRoleService.addRoleToUser(id, roleName)
            respond result, status: HttpStatus.OK
        }catch (IllegalArgumentException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }

    def removeRoleFromUser(){
        def jsonRequest = request.JSON
        Long id = jsonRequest.id
        String roleName = jsonRequest.roleName

        try {
            def result = userRoleService.removeRoleFromUser(id, roleName)
            respond result, status: HttpStatus.OK
        }catch (IllegalArgumentException e){
            respond([message: e.message], status: HttpStatus.BAD_REQUEST)
        }
    }
}