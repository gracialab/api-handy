package handy.api

import grails.validation.Validateable

/**
 * Clase que encapsula los criterios de búsqueda para productos.
 */
class ProductSearchCriteria implements Validateable {

    String name
    BigDecimal minPrice
    BigDecimal maxPrice
    String category
    Boolean inactive

    static constraints = {
        name(nullable: true, blank: true) // Nombre del producto, opcional
        minPrice(nullable: true) // Precio mínimo, opcional
        maxPrice(nullable: true) // Precio máximo, opcional
        category(nullable: true, blank: true) // Categoría, opcional
        inactive(nullable: true) // Filtrar por estado de inactividad, opcional
    }
}
