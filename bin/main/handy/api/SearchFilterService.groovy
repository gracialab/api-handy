package handy.api

class SearchFilterService {

    def saveFilter(Map params) {
        def searchFilter = new SearchFilter(params)
        return searchFilter.save(flush: true)
    }

    def getAllFilters() {
        return SearchFilter.list()
    }

    def deleteFilter(Long id) {
        def searchFilter = SearchFilter.get(id)
        if (searchFilter) {
            searchFilter.delete(flush: true)
            return true
        }
        return false
    }
}
