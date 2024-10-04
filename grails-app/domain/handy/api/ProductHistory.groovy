package handy.api

import handy.api.Categoria

class ProductHistory {

    Product product  // Referencia al producto original
    String name
    String product_description
    BigDecimal price
    Integer stock
    byte[] imagen
    Categoria categoria
    Date editDate = new Date()  // Fecha de la edición
    String editedBy  // Información sobre quién hizo la edición
    
    static constraints = {
        name blank: false, nullable: false
        product_description blank: false, nullable: false
        price nullable: false
        stock nullable: false
        categoria nullable: false
        imagen nullable: true
    }

    static mapping = {
        version false
    }
}
