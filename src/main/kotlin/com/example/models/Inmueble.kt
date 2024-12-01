package com.example.models

import org.jetbrains.exposed.sql.Table

data class Inmueble(
    val idInmueble: Int,
    val direccion: String,
    val tipoInmueble: String,
    val estadoInmueble: String,
    val folioPEmergencia: Int
)

object Inmuebles : Table() {
    val idInmueble = integer("idInmueble").autoIncrement()
    val direccion = varchar("direccion", 255)
    val tipoInmueble = varchar("tipoInmueble", 50)
    val estadoInmueble = varchar("estadoInmueble", 50)
    val folioPEmergencia = integer("folioPEmergencia").references(ParteEmergencias.folioPEmergencia)

    override val primaryKey = PrimaryKey(idInmueble)
}
