package handy.api

class SearchFilter {
    String name // Nombre del filtro
    Map criteria // Un mapa para almacenar los criterios de búsqueda
    User user // Relación con el usuario que guarda el filtro

    static belongsTo = [user: User]

    static constraints = {
        name nullable: false, blank: false // El nombre del filtro no puede estar vacío
        criteria nullable: false // Los criterios son obligatorios
    }
}
