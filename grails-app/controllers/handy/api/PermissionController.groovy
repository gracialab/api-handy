package handy.api

import grails.gorm.transactions.Transactional


class PermissionController {

    PermissionService permissionService

    @Transactional
    def save() {
        def json = request.JSON
        def permission = new Permission(json)

        if (permission.save(flush: true)) {
            render(status: 201, text: "Permission created")
        } else {
            render(status: 400, text: permission.errors.allErrors.collect { it.defaultMessage }.join(', '))
        }
    }
}