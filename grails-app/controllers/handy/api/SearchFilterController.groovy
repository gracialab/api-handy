package handy.api

class SearchFilterController {

    SearchFilterService searchFilterService

    def index() {
        respond searchFilterService.getAllFilters()
    }

    def save() {
        if (searchFilterService.saveFilter(params)) {
            flash.message = "Filtro de búsqueda guardado con éxito."
            redirect action: "index"
        } else {
            flash.message = "Error al guardar el filtro de búsqueda."
            render(view: "create", model: [searchFilter: new SearchFilter(params)])
        }
    }

    def delete(Long id) {
        if (searchFilterService.deleteFilter(id)) {
            flash.message = "Filtro de búsqueda eliminado."
        } else {
            flash.message = "Filtro de búsqueda no encontrado."
        }
        redirect action: "index"
    }
}
