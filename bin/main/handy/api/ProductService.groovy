package handy.api

import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    def addProduct(params) {
        // Crear un nuevo producto basado en los parámetros recibidos
        def product = new Product(params)
        if (product.save()) {
            return product
        } else {
            return null // Manejar errores o validaciones fallidas
        }
    }

    def updateProduct(Long id, params) {
        // Actualizar el producto existente
        def product = Product.get(id)
        if (product) {
            product.properties = params
            if (product.save()) {
                return product
            } else {
                return null // Manejar errores en la actualización
            }
        } else {
            return null // Producto no encontrado
        }
    }

    def deleteProduct(Long id) {
        // Eliminar (inactivar) un producto
        def product = Product.get(id)
        if (product) {
            product.inactive = true // Marcar como inactivo en lugar de eliminar
            product.save()
        }
    }

    def getProduct(Long id) {
        // Obtener un producto por su ID
        return Product.get(id)
    }

    def listProducts() {
        // Listar todos los productos activos
        return Product.findAllByInactive(false) // Solo mostrar productos activos
    }
}
