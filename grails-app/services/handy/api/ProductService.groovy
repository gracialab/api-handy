package handy.api

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import handy.api.ProductSearchCriteria


/**
 * Servicio para gestionar operaciones relacionadas con los productos,
 * incluyendo creación, actualización, eliminación, búsqueda avanzada y manejo de historial.
 */

class ProductService {

    /**
     * Guarda un producto en la base de datos.
     * 
     * @param product El objeto Product a guardar.
     * @throws ValidationException Si los datos del producto son inválidos.
     * @return El producto guardado.
     */
    @Transactional 
    def saveProduct(Product product) {
        if (!product.validate()) {
            product.errors.each { println it }
            throw new ValidationException("Datos inválidos: ${product.errors.allErrors.collect { it.defaultMessage }.join(', ')}", product.errors)
        }

        if (product.save(flush: true)) {
            return product
        } else {
            throw new ValidationException("Error al guardar el producto", product.errors)
        }
    }

    /**
     * Actualiza un producto existente y guarda su historial.
     * 
     * @param product El objeto Product a actualizar.
     * @throws ValidationException Si los datos del producto son inválidos.
     * @return El producto actualizado.
     */
    def updateProduct(Product product) {
        // Guardar la versión actual en el historial antes de actualizar
        saveProductHistory(product)
        validateAndSave(product)
    }

    /**
     * Desactiva (soft delete) un producto estableciendo el campo 'inactive' en true.
     * 
     * @param id El ID del producto a desactivar.
     * @throws ValidationException Si ocurre un error al desactivar el producto.
     * @throws IllegalArgumentException Si el producto no existe.
     */
    def deactivateProduct(Long id) {
        def product = Product.findById(id)
        if (product) {
            product.inactive = true
            if (!product.save(flush: true)) {
                throw new ValidationException("Error al desactivar el producto", product.errors)
            }
        } else {
            throw new IllegalArgumentException("Producto no encontrado")
        }
    }

    /**
     * Elimina un producto de forma permanente.
     * 
     * @param id El ID del producto a eliminar.
     * @throws IllegalArgumentException Si el producto no existe.
     */
    def deleteProduct(Long id) {
        def product = Product.findById(id)
        if (product) {
            product.delete(flush: true)
        } else {
            throw new IllegalArgumentException("Producto no encontrado")
        }
    }

    /**
     * Realiza una búsqueda avanzada de productos basado en criterios específicos.
     * 
     * @param criteria Criterios de búsqueda encapsulados en un objeto ProductSearchCriteria.
     * @return Lista de productos que coinciden con los criterios de búsqueda.
     */
    List<Product> searchProducts(ProductSearchCriteria criteria) {
        def query = Product.createCriteria().list {
            if (criteria.name) {
                ilike('name', "%${criteria.name}%")
            }
            if (criteria.category) {
                eq('category', criteria.category)
            }
            if (criteria.minPrice) {
                ge('price', criteria.minPrice)
            }
            if (criteria.maxPrice) {
                le('price', criteria.maxPrice)
            }
        }

        return query
    }

    /**
     * Guarda un historial de la versión actual del producto antes de actualizarlo.
     * 
     * @param product El producto cuyo historial se guardará.
     */
    private saveProductHistory(Product product) {
        new ProductHistory(
            product: product,
            name: product.name,
            product_description: product.product_description,
            price: product.price,
            stock: product.stock,
            imagen: product.imagen,
            categoria: product.categoria,
            editedBy: "admin" 
        ).save(flush: true)
    }

    /**
     * Método para revertir un producto a una versión anterior.
     * 
     * @param productId El ID del producto a revertir.
     * @param historyId El ID del historial al que se revertirá.
     * @throws IllegalArgumentException Si el producto o el historial no existen.
     */
    def revertProduct(Long productId, Long historyId) {
        def product = Product.findById(productId)
        def history = ProductHistory.findById(historyId)
        
        if (product && history) {
            product.name = history.name
            product.product_description = history.product_description
            product.price = history.price
            product.stock = history.stock
            product.imagen = history.imagen
            product.categoria = history.categoria
            product.save(flush: true)
        } else {
            throw new IllegalArgumentException("Producto o historial no encontrado")
        }
    }

    /**
     * Método privado para validar y guardar un producto.
     * 
     * @param product El objeto Product a validar y guardar.
     * @throws ValidationException Si los datos son inválidos.
     * @return El producto guardado.
     */
    private validateAndSave(Product product) {
        if (!product.validate()) {
            throw new ValidationException("Datos inválidos: ${product.errors.allErrors.collect { it.defaultMessage }.join(', ')}", product.errors)
        }

        if (!product.save(flush: true)) {
            throw new ValidationException("Error al guardar el producto", product.errors)
        }

        return product
    }
}
