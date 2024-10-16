package handy.api

import handy.api.Categoria
import java.math.BigDecimal

class Product {    
   
    String name  // nombre del producto
    String product_description // descripción del producto
    BigDecimal price  // El precio del producto
    Integer stock  // cantidad disponible
    byte[] imagen
    Date create_at = new Date()
    Date update_at  = new Date()
    Categoria categoria  // relación con la clase Categoria
    boolean inactive = false  // Nuevo campo para manejar inactivación

    // Relación muchos a muchos con ProductOrder a través de una tabla intermedia product_order
    static hasMany = [productsOrder: ProductOrder] 

    // Definición de las columnas y la tabla de unión para la relación con ProductOrder
    static mapping = {
        version false  // Deshabilita la versión para esta entidad
        order joinTable: [name: 'product_order', key: 'product_id', column: 'order_id']
    }  

    // Método para verificar si hay suficiente stock
    boolean hasStock(int requestedQuantity) {
        return stock >= requestedQuantity
    }

    // --- Restricciones (constraints) adaptadas de la clase Producto ---
    static constraints = {
        name blank: false, nullable: false  // nombre es obligatorio
        product_description blank: false, nullable: false  // descripción es obligatoria
        imagen nullable: true  // imagen es opcional
        stock nullable: false, min: 0  // cantidad es obligatoria y debe ser positiva
        categoria nullable: false  // categoría es obligatoria
        price nullable: false, min: 0.0  // precio es obligatorio y debe ser positivo
        inactive nullable: false  // no debe ser nulo
        update_at nullable: true
    }
}
