package handy.api

class UrlMappings {

    static mappings = {
        "/users"(resources: 'user') 
        // CRUD básico
        delete "/$controller/$id(.$format)?"(action: "delete")
        get "/$controller(.$format)?"(action: "index")
        get "/$controller/$id(.$format)?"(action: "show")
        post "/$controller(.$format)?"(action: "save")
        put "/$controller/$id(.$format)?"(action: "update")
        patch "/$controller/$id(.$format)?"(action: "patch")

        // Rutas personalizadas para User
        "/saveUser"(controller: "User", action: "save", method: "POST")
        "/updateUser/$id"(controller: "User", action: "update", method: "PUT")
        "/deleteUser/$id"(controller: "User", action: "delete", method: "DELETE")
        "/deactivateUser/$id"(controller: "User", action: "deactivate", method: "PUT")
        "/searchUsers"(controller: "User", action: "searchUsers", method: "POST")

        // Rutas personalizadas para SearchFilter
        
        "/saveFilter"(controller: "SearchFilter", action: "saveFilter", method: "POST")
        "/applyFilter/$userId"(controller: "SearchFilter", action: "applyFilter", method: "GET")
        "/deleteFilter"(controller: "SearchFilter", action: "deleteFilter", method: "DELETE")

        // Rutas personalizadas para Product
        
        "/saveProduct"(controller: "Product", action: "save", method: "POST")
        "/updateProduct/$id"(controller: "Product", action: "update", method: "PUT")
        "/deleteProduct/$id"(controller: "Product", action: "delete", method: "DELETE")
        "/deactivateProduct/$id"(controller: "Product", action: "deactivate", method: "PUT")
        "/searchProducts"(controller: "Product", action: "searchProducts", method: "POST")
        "/products/$id"(controller: "Product", action: "show", method: "GET")


        // Ruta para gestionar el historial de productos
        "/productHistory"(controller: "ProductHistory", action: "index", method: "GET")
        "/productHistory/$id"(controller: "ProductHistory", action: "show", method: "GET")
        "/productHistory/save"(controller: "ProductHistory", action: "save", method: "POST")

        // Rutas personalizadas para Order (pedidos)
        "/getClients"(controller: "Order", action: "getClients", method: "GET")
        "/saveOrder"(controller: "Order", action: "save", method: "POST")
        "/Order/$id"(controller: 'Order', action: 'show') {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        "/updateOrder/$id"(controller: "Order", action: "update") {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        "/updateStateOrder/$id"(controller: "Order", action: "updateState",  method: "PUT") {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        "/updateAssignClient/$id"(controller: "Order", action: "updateClient", method: "PUT") {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        // Rutas personalizadas para Recibos de venta (salesReceipt)
        "/SalesReceipt/$id"(controller: 'SalesReceipt', action: 'getSalesReceipt') {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        "/salesReceipt/getPdf/$id"(controller: "SalesReceipt", action: "generatePdf", method: "GET")
        "/salesReceipt/sendMail/$id"(controller: "SalesReceipt", action: "sendEmail", method: "GET")
        // Rutas personalizadas para novedades
        "/novelty/save"(controller: "Novelty", action: "save", method: "POST")
        "/novelty/adjustInventory/$id"(controller: "Novelty", action: "adjustInventory", method:"PUT")

        "/reports/generate"(controller: "Report", action: "downloadSalesReport")
        // Página principal y manejo de errores
        "/"(controller: 'application', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')

        // Rutas para autenticacion
        "/login/register"(controller: "userRegistration", action: "register", method: "POST")
        "/verifyAccount"(controller: "userRegistration", action: "verifyAccount", method: "GET")
        "/login"(controller: "login", action: "auth", method: "POST")

        "/password/forgot"(controller: "passwordReset", action: "forgotPassword", method: "POST")
        "/password/reset"(controller: "passwordReset", action: "resetPassword")

        // Rutas para gestionar Roles
        "/role/save"(controller: "role", action: "save", method: "POST")
        "/role/list"(controller: "role", action: "list", method: "GET")
        "/role/update/$id"(controller: "role", action: "update", method: "PUT")
        "/role/delete/$id"(controller: "role", action: "delete", method: "DELETE")

        "/role/addRoleToUser"(controller: "userRole", action: "addRoleToUser", method: "POST")
        "/role/removeRoleFromUser"(controller: "userRole", action: "removeRoleFromUser", method: "POST")
        "/role/addPermissionToRole"(controller: "role", action: "addPermissionToRole", method: "POST")

        // Rutas para gestionar Permisos
        "/permission"(controller: "permission", action: "save", method: "POST")
        "/permission/list"(controller: "permission", action: "list", method: "GET")
        "/permission/update/$id"(controller: "permission", action: "update", method: "PUT")
        "/permission/delete/$id"(controller: "permission", action: "delete", method: "DELETE")

        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}
