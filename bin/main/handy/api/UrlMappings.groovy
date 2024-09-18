package handy.api

class UrlMappings {

    static mappings = {
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
        
        // Página principal y manejo de errores
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
