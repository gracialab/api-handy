package handy.api

class UrlMappings {
    static mappings = {
        // Mapeo específico para POST a /user/save
        "/user/save"(controller: "user", action: "save", method: "POST")
        
        // Mapeo específico para PUT a /user/update
        "/user/update"(controller: "user", action: "update", method: "PUT")

        // Mapeo específico para DELETE a /user/delete/{id}
        "/user/delete/$id"(controller: "user", action: "delete", method: "DELETE")

        // Mapeos RESTful para recursos genéricos (solo si se usa en otros controladores)
        "/$controller/$id(.$format)?"(action:"show") // GET /controller/1
        "/$controller(.$format)?"(action:"index")   // GET /controller
        "/$controller/$id(.$format)?"(action:"update") // PUT /controller/1
        "/$controller/$id(.$format)?"(action:"delete") // DELETE /controller/1
        "/$controller(.$format)?"(action:"save")     // POST /controller
        "/$controller/$id(.$format)?"(action:"patch") // PATCH /controller/1
        
        "/"(controller: 'application', action:'index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
