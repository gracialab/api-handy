package handy.api

import grails.validation.ValidationException
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.springframework.validation.FieldError

@CompileStatic
class NoveltyService {

    def saveNovelty(json) {
        try {
            def novelty = new Novelty(json as Map)
            if (!novelty.validate()) {
                return messageResponseErrors(false, "Error en validaciones", novelty.errors.allErrors)
            }
            novelty.save(flush: true)
            return messageResponse(true, "Novedad registrada correctamente", novelty)
        } catch (Exception ex) {
            ex.printStackTrace()
            return [valid   : false, mensaje : "error"]
        }
    }

    def messageResponseErrors(valid, message, allErrors) {
      return [valid  : valid,
         mensaje: message,
         errores: allErrors.collect { error ->
             if (error instanceof FieldError) {
                 [field  : error.field,
                  message: error.defaultMessage]
             } else {
                 [message: error.toString()]
             }
         }]
    }
    def messageResponse(valid, message, data) {
        return [valid: valid, mensaje : message, data: data]
    }
}