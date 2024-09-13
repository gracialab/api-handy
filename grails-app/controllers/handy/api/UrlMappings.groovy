package handy.api

class UrlMappings {
    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        "/deleteUser/$id"(controller: "User", action: "delete", method: "DELETE")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        "/saveUser"(controller: "User", action: "save", method: "POST")
        put "/$controller/$id(.$format)?"(action:"update")
        "/updateUser/$id"(controller: "User", action: "update", method: "PUT")  
        "/deactivateUser/$id"(controller: "User", action: "deactivate", method: "PUT")           
        patch "/$controller/$id(.$format)?"(action:"patch")
        "/"(controller: 'application', action:'index')
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}