package handy.api

class ProductController {

    ProductService productService

    def index() {
        // Listado de productos
        def products = productService.listProducts()
        [products: products]
    }

    def create() {
        // Crear nuevo producto
        def product = new Product()
        [product: product]
    }

    def save() {
        // Guardar producto usando el servicio
        def product = productService.addProduct(params)
        if (product) {
            flash.message = "Product created successfully"
            redirect(action: "index")
        } else {
            flash.message = "Error creating product"
            render(view: "create", model: [product: product])
        }
    }

    def edit() {
        // Editar producto
        def product = productService.getProduct(params.id)
        if (product) {
            [product: product]
        } else {
            flash.message = "Product not found"
            redirect(action: "index")
        }
    }

    def update() {
        // Actualizar producto usando el servicio
        def product = productService.updateProduct(params.id, params)
        if (product) {
            flash.message = "Product updated successfully"
            redirect(action: "index")
        } else {
            flash.message = "Error updating product"
            render(view: "edit", model: [product: product])
        }
    }

    def delete() {
        // Eliminar o inactivar producto
        productService.deleteProduct(params.id)
        flash.message = "Product inactivated successfully"
        redirect(action: "index")
    }
}
