package handy.api

import grails.converters.JSON

class SearchFilterController {

    SearchFilterService searchFilterService
    
    // Guardar filtro
    def saveFilter() {
        println "Parámetros recibidos: ${params}"
        def user = User.get(params.userId) // Obtener el usuario basado en el parámetro userId
        if (!user) {
            render(status: 400, json: [error: "Criterios de búsqueda inválidos o ausentes"])
            return
        }

        def filterName = params.filterName
        def criteria = params.criteria // Los criterios como un mapa (deberías recibirlo correctamente en el POST)

        def filter = searchFilterService.saveFilter([name: filterName, criteria: criteria], user)
        if (filter) {
            render(status: 200, json: [message: "Filtro guardado correctamente", filterId: filter.id])
        } else {
            render(status: 500, json: [error: "Error al guardar el filtro"])
        }
    }

    // Aplicar filtro guardado
    def applyFilter(Long filterId) {
        def filter = SearchFilter.get(filterId)
        if (filter) {
            def users = searchFilterService.applyFilter(filter)
            render users as JSON
        } else {
            render(status: 404, json: [error: "Filtro no encontrado"])
        }
    }

    // Eliminar filtro guardado
    def deleteFilter(Long id) {
        def user = User.get(params.userId) // Obtener el usuario basado en el parámetro userId
        if (!user) {
            render(status: 404, json: [error: "Usuario no encontrado"])
            return
        }

        def deleted = searchFilterService.deleteFilter(id, user)
        if (deleted) {
            render(status: 200, json: [message: "Filtro eliminado correctamente"])
        } else {
            render(status: 404, json: [error: "Filtro no encontrado o no pertenece al usuario"])
        }
    }
}
