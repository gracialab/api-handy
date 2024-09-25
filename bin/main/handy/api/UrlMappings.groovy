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
        "/searchFilter"(controller: "searchFilter", action: "index", method: "GET")
        "/searchFilter/save"(controller: "searchFilter", action: "save", method: "POST")
        "/searchFilter/delete/$id"(controller: "searchFilter", action: "delete", method: "DELETE")


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

        "500"(view: '/error')
        "404"(view: '/notFound')

    }
}
