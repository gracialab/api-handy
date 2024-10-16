package handy.api

class Categoria {
    String nombre
    String descripcion

    static constraints = {
        nombre blank: false, nullable: false
        descripcion nullable: true
    }

    static mapping = {
        version false
    }
}
