package handy.api

import grails.rest.RestfulController
import grails.validation.ValidationException
import org.springframework.http.HttpStatus
import grails.gorm.transactions.Transactional

/**
 * Controlador REST para gestionar productos.
 * Maneja las operaciones CRUD para productos, incluyendo su creación, edición, eliminación y búsqueda.
 */
class ProductController extends RestfulController<Product> {

    static responseFormats = ['json']

    // Inyectamos el servicio ProductService
    ProductService productService

    ProductController() {
        super(Product)
    }

    /**
     * Guarda un nuevo producto utilizando los datos recibidos.
     */
    @Transactional
    def save() {
        def jsonData = request.JSON
        println "Datos recibidos: ${jsonData.dump()}"

        // Validación de campos obligatorios
        if (!jsonData.name || !jsonData.product_description || !jsonData.stock || !jsonData.categoria) {
            render status: HttpStatus.BAD_REQUEST.value(), text: "Los campos 'name', 'product_description', 'stock' y 'categoria' son obligatorios"
            return
        }

        // Asegurarse de que la categoría existe
        def categoria = Categoria.get(jsonData.categoria.id)
        if (!categoria) {
            render status: HttpStatus.BAD_REQUEST.value(), text: "La categoría con ID ${jsonData.categoria.id} no existe."
            return
        }

        def product = new Product(
            name: jsonData.name,
            product_description: jsonData.product_description,
            imagen: jsonData.imagen ?: null,
            stock: jsonData.stock,
            price: jsonData.price,
            categoria: Categoria.get(jsonData.categoria.id),
            create_at: new Date(),
            update_at: new Date()
        )

        try {
            productService.saveProduct(product)

            // Crear una entrada de historial después de guardar el producto
            productService.saveProductHistory(product)

            respond product, status: HttpStatus.CREATED
        } catch (ValidationException e) {
            //renderValidationErrors(product)
            println(e)
        } catch (Exception e) {
            renderInternalServerError(e)
        }
    }

    /**
     * Muestra un producto por ID.
     */
    def show(Long id) {
        def product = Product.findById(id)
        if (product) {
            respond product
        } else {
            render status: HttpStatus.NOT_FOUND.value(), text: 'Producto no encontrado'
        }
    }

    /**
     * Lista todos los productos.
     */
    def index() {
        def products = Product.list()
        def productData = products.collect { product ->
            return [
                id: product.id,
                name: product.name,
                product_description: product.product_description,
                stock: product.stock,
                categoria: product.categoria,
                imagen: product.imagen
            ]
        }
        respond productData
    }

    /**
     * Actualiza un producto existente por ID.
     */
     @Transactional
    def update(Long id) {
        def jsonData = request.JSON
        println "Datos recibidos para actualizar: ${jsonData}"

        def product = Product.findById(id)
        if (!product) {
            render status: HttpStatus.NOT_FOUND.value(), text: 'Producto no encontrado'
            return
        }

        updateProductFields(product, jsonData)

        try {
            productService.updateProduct(product)

            // Crear una entrada de historial después de actualizar el producto
            productService.saveProductHistory(product)

            respond product, status: HttpStatus.OK
        } catch (ValidationException e) {
            // renderValidationErrors(product)
            println('Aqui entro')
        } catch (Exception e) {
            e.printStackTrace()
            println('Aqui tambien entro')
        //renderInternalServerError(e)
        }
    }

    /**
     * Elimina un producto por ID con confirmación previa.
     */
    @Transactional
    def delete(Long id) {
        def product = Product.findById(id)

        if (!product) {
            render status: HttpStatus.NOT_FOUND.value(), text: 'Producto no encontrado'
            return
        }

        // Verificar si se ha recibido la confirmación de eliminación
        def confirm = params.confirm ?: request.JSON.confirm
        if (!confirm || confirm != 'yes') {
            render status: HttpStatus.CONFLICT.value(),
                    text: "¿Está seguro de que desea eliminar este producto? Esta acción no puede deshacerse. Confirme con 'yes'."
            return
        }

        try {
            // Registrar en el historial antes de eliminar el producto (opcional)
            //productService.saveProductHistory(product)

            //Elimina el historial de productos relacionados
            ProductHistory.where { product == product }.deleteAll()

            // Luego elimina el producto
            product.delete(flush: true)

            render(status: HttpStatus.OK, text: 'Producto eliminado correctamente')
        } catch (Exception e) {
            render status: HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    text: "Error al eliminar el producto: ${e.message}"
        }
    }

    // Métodos auxiliares para actualizar campos y manejar errores
    private void updateProductFields(Product product, def jsonData) {
        product.name = jsonData.name ?: product.name
        product.product_description  = jsonData.product_description  ?: product.product_description
        product.stock = jsonData.stock ?: product.stock
        product.categoria = jsonData.categoria ?: product.categoria
        product.price = jsonData.price ?: product.price
        product.imagen = jsonData.imagen ?: product.imagen
        product.update_at = new Date()
    }

    /*private void renderValidationErrors(Product product) {
        if (product.errors.allErrors.size() > 0) {
            def errorMessages = product.errors.allErrors.collect { error ->
                "${error.field}: ${error.defaultMessage}"
            }.join(', ')
            render status: HttpStatus.UNPROCESSABLE_ENTITY.value(), text: "Datos inválidos: ${errorMessages}"
        }
    }
    */

    private void renderInternalServerError(Exception e) {
        def stackTrace = e.stackTrace.collect { "${it}" }.join('\n')
        render status: HttpStatus.INTERNAL_SERVER_ERROR.value(), text: "Error interno del servidor: ${e.message}\nStacktrace:\n${stackTrace}"
    }

}
