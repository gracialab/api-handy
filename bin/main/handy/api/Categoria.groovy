package handy.api

class Categoria {
    String nombre
    String descripcion

    static hasMany = [productos: Product]  // Relación uno a muchos

    static constraints = {
        nombre blank: false, nullable: false
        descripcion nullable: true
    }
}
