package handy.api

import java.time.Instant

/**
 * Clase que representa un usuario dentro del sistema.
 *
 * Esta clase contiene la información personal del usuario, así como los
 * detalles de su historial de compras, roles asociados, y preferencias.
 */
class User {

    // Propiedades del usuario
    String name                // Nombre del usuario
    String lastname            // Apellido del usuario (opcional)
    String username            // Nombre de usuario, debe ser único
    String email               // Correo electrónico del usuario, debe ser único
    String password            // Contraseña del usuario
    String phone               // Número de teléfono del usuario (10 dígitos)
    String address             // Dirección del usuario (opcional)
    String preferences         // Preferencias del usuario (opcional)
    Date updateAt              // Fecha de la última actualización
    Boolean active = true      // Estado del usuario (activo o desactivado)
    String purchaseHistory     // Historial de compras del usuario (opcional)
    String verification_token
    Instant token_expiration
    Boolean is_register = false
//    Boolean verified = false

    // Relación con la clase Role (un usuario puede tener varios roles)
    static hasMany = [roles: Role]
    static belongsTo = Role

    // Mapeo de la base de datos
    static mapping = {
        version false
        roles joinTable: [name: 'role_user', key: 'user_id', column: 'role_id']  // Define la tabla intermedia para la relación muchos a muchos
        table 't_user'                                                           // Define la tabla donde se almacena la información del usuario
        id column: 'identification'                                              // Define la columna 'identification' como clave primaria
    }

    // Reglas de validación para los campos
    static constraints = {
        name blank: false                                                        // El campo 'name' es obligatorio
        lastname blank: false                                                  // El campo 'lastname' es opcional
        username blank: false, unique: true                                      // 'username' es obligatorio y debe ser único
        email email: true, blank: false, unique: true                            // 'email' es obligatorio, debe tener un formato válido y ser único
        password blank: false                                                    // 'password' es obligatorio
        phone matches: "\\d{10}", blank: false                                   // 'phone' es obligatorio y debe tener 10 dígitos
        address nullable: true                                                   // 'address' es opcional
        preferences nullable: true                                               // 'preferences' es opcional
        purchaseHistory nullable: true                                           // 'purchaseHistory' es opcional
        verification_token nullable: true
        token_expiration nullable: true
        is_register nullable: true
        updateAt nullable: true
        password nullable: true, blank: false, validator: { val, obj ->
            if (obj.is_register && (!val || !obj.validatePassword(val))) {
                return ['invalidPassword']
            }
        }
    }

    def validatePassword(String password){
        def passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/

        return password.matches(passwordPattern)
    }
}