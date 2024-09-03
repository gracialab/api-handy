package handy.api

class UrlMappings {
    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        "/saveOrder"(controller: "Order", action: "save", method: "POST")
        "/Order/$id"(controller: 'Order', action: 'show') {
            constraints {
                id(matches: /\d+/) // Asegura que el ID sea un número
            }
        }
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        "/"(controller: 'application', action:'index')
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
