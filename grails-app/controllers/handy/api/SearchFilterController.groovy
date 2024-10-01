package handy.api

import grails.converters.JSON

class SearchFilterController {

    SearchFilterService searchFilterService

    // Guardar filtro
    def saveFilter() {
        println "Parámetros recibidos: ${params}"
        def user = User.get(request.JSON.userId)
        
        if (!user) {
            render(contentType: 'application/json', status: 400, "Usuario no encontrado")
            return
        }

        def filterName = request.JSON.filterName
        def criteria = request.JSON.criteria 

        if (!filterName || !criteria) {
            render(contentType: 'application/json', status: 400, "Nombre del filtro o criterios son inválidos")
            return
        }

        def filter = searchFilterService.saveFilter([name: filterName, criteria: criteria], user)
        
        if (filter) {
            render(contentType: 'application/json', status: 201, "Filtro guardado correctamente")
        } else {
            render(contentType: 'application/json', status: 500, "Error al guardar el filtro")
        }
    }

    // Aplicar filtro guardado
    def applyFilter(Long userId) {
        def user = User.get(userId)
        
        if (!user) {
            render(contentType: 'application/json', status: 404, "Usuario no encontrado")
            return            
        }

        def filters = SearchFilter.findAllByUser(user)
        if (filters.isEmpty()) {
            render(contentType: 'application/json', status: 404, "No se encontraron filtros para el usuario")
            return
        }

        def users = searchFilterService.applyFilter(filters)
        def jsonUsers = users.collect { [
            id: it.id,
            name: it.name,        
        ] }.unique { it.id }
        render contentType: 'application/json', jsonUsers as JSON
    }

    // Eliminar filtro guardado
    def deleteFilter() {
        def userId = params.userId // Obtener el ID del usuario de los parámetros
        def filterName = params.filterName // Obtener el nombre del filtro de los parámetros
        
        def user = User.get(userId)
        if (!user) {
            render(contentType: 'application/json', status: 404, "Usuario no encontrado")
            return
        }
        
        if (!filterName) {
            render(contentType: 'application/json', status: 400, "Nombre del filtro no proporcionado")
            return
        }

        def success = searchFilterService.deleteFilter(filterName, user)
        if (success) {
            render(contentType: 'application/json', status: 200, "Filtro eliminado correctamente")
        } else {
            render(contentType: 'application/json', status: 404, "Filtro no encontrado o no pertenece al usuario")
        }
    }
}
