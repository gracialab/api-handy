package handy.api

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.transform.CompileStatic
import grails.plugins.metadata.*
import io.github.cdimascio.dotenv.Dotenv

@CompileStatic
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        Dotenv dotenv = Dotenv.load()
        // Establece las variables de entorno para usarlas en el application.yml
        System.setProperty("DB_URL", dotenv.get("DB_URL"))
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"))
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"))
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"))

        GrailsApp.run(Application, args)
    }
}
