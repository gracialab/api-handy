package handy.api

class Categoria {
    Integer id
    String name  // Nombre de la categoría
    String description  // Descripción de la categoría
    boolean inactive = false  // Campo para manejar inactivación

    // Relación uno a muchos con Product
    static hasMany = [products: Product] 

    // Definición de las columnas
    static mapping = {
        version false  // Deshabilita la versión para esta entidad
    }

    // Restricciones (constraints)
    static constraints = {
        name blank: false, nullable: false  // El nombre no puede estar en blanco ni ser nulo
        description nullable: true  // La descripción es opcional
        inactive nullable: false  // No debe ser nulo
    }
}
