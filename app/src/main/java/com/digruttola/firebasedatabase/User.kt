package com.digruttola.firebasedatabase

import java.util.*

class User {

    var id : String
    var name : String
    var apellido : String
    var edad : Int
    var fechaNacimiento : Date
    var dni : Long
    var email : String

    constructor(
        id : String,
        name: String,
        apellido: String,
        edad: Int,
        fechaNacimiento: Date,
        dni: Long,
        email: String
    ) {
        this.id = id
        this.name = name
        this.apellido = apellido
        this.edad = edad
        this.fechaNacimiento = fechaNacimiento
        this.dni = dni
        this.email = email
    }

}