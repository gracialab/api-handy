package handy.api

import grails.gorm.transactions.Transactional

class SearchFilterService {

    // Método para guardar un filtro de búsqueda   
    @Transactional 
    def saveFilter(Map params, User user) {
        def searchFilter = new SearchFilter(name: params.name, criteria: params.criteria, user: user)

        // Validar el objeto antes de guardarlo
        if (!searchFilter.validate()) {
            throw new IllegalArgumentException("Filtros inválidos: ${searchFilter.errors}")
        }

        try {
            return searchFilter.save(flush: true)
        } catch (Exception e) {
            e.printStackTrace()
            log.error("Error al guardar el filtro: ${e.message}", e)
            return null
        }
    }

    // Método para obtener todos los filtros de un usuario
    def getAllFilters(User user) {
        return SearchFilter.findAllByUser(user)
    }

    // Método para eliminar un filtro por nombre
    @Transactional
    def deleteFilter(String filterName, User user) {
        def searchFilter = SearchFilter.findByUserAndName(user, filterName)
        if (searchFilter) {
            try {
                searchFilter.delete(flush: true)
                return true
            } catch (Exception e) {
                log.error("Error al eliminar el filtro: ${e.message}", e)
                return false
            }
        }
        return false
    }

    // Método para aplicar un filtro guardado y buscar usuarios
    List<Map> applyFilter(List<SearchFilter> filters) {
        def results = []
        filters.each { filter ->
            def criteria = User.createCriteria()
            def tempResults = criteria.list {
                filter.criteria.each { key, value -> 
                    ilike(key, "%${value}%") // Filtra los usuarios basándose en criterios 
                }
            }
            tempResults.each { user ->
                results << [
                    id: user.id,
                    name: user.name,
                ]
            }
        }
        return results
    }
}
