package handy.api

class SearchFilterService {

    // Método para guardar un filtro de búsqueda
    def saveFilter(Map params, User user) {
        def searchFilter = new SearchFilter(name: params.name, criteria: params.criteria, user: user)
        return searchFilter.save(flush: true)
    }

    // Método para obtener todos los filtros
    def getAllFilters(User user) {
        return SearchFilter.findAllByUser(user)
    }

    // Método para eliminar un filtro
    def deleteFilter(Long id, User user) {
        def searchFilter = SearchFilter.findByIdAndUser(id, user)
        if (searchFilter) {
            searchFilter.delete(flush: true)
            return true
        }
        return false
    }

    // Método para aplicar un filtro guardado y buscar usuarios
    List<User> applyFilter(SearchFilter filter) {
        def criteria = User.createCriteria()
        return criteria.list {
            filter.criteria.each { key, value ->
                ilike(key, "%${value}%") // Filtra los usuarios basándose en criterios 
            }
        }
    }
}
