package handy.api

class Product {
    
    Integer id
    String name  // nombre en la clase Producto
    String product_description // descripcion en la clase Producto
    BigDecimal price  // El precio no estaba en Producto, pero se mantiene aquí
    Integer stock  // cantidad en la clase Producto
    Date create_at = new Date()
    Date update_at  = new Date()
    byte[] imagen
    Categoria categoria
    boolean inactive = false  // Nuevo campo para manejar inactivación

    // Relación muchos a muchos con ProductOrder a través de una tabla intermedia product_order
    static hasMany = [productsOrder: ProductOrder] 

    // Definición de las columnas y la tabla de unión para la relación con ProductOrder
    static mapping = {
        version false  // Deshabilita la versión para esta entidad
        order joinTable: [name: 'product_order', key: 'product_id', column: 'order_id']
    }

 
    // Nueva propiedad: Imagen en formato byte[]
    byte[] imagen // Se permite que la imagen sea opcional (nullable en los constraints)
    
    // Relación con la clase Categoria, misma que en Producto
    Categoria categoria // nullable: false, por lo que la categoría es obligatoria

    // Métodos adicionales
    // Método para verificar si hay suficiente stock
    boolean hasStock(int requestedQuantity) {
        return stock >= requestedQuantity
    }

    // --- Restricciones (constraints) adaptadas de la clase Producto ---
    static constraints = {
        name blank: false, nullable: false  // nombre en Producto
        product_description blank: false, nullable: false  // descripcion en Producto
        imagen nullable: true  // imagen es opcional en Producto
        stock nullable: false  // cantidad en Producto, debe ser obligatoria
        categoria nullable: false  // categoría es obligatoria en Producto
        price nullable: false  // price es obligatorio aunque no esté en Producto
        inactive nullable: false  // No debe ser nulo
    }
}
