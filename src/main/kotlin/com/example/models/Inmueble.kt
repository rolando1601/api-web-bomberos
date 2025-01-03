package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Inmuebles(
    val idInmueble: Int? = null,
    val direccion: String,
    val tipoInmueble: String,
    val estadoInmueble: String,
    val folioPEmergencia: Int?
)

object Inmueble : Table() {
    val idInmueble = integer("idInmueble").autoIncrement()
    val direccion = varchar("direccion", 255)
    val tipoInmueble = varchar("tipoInmueble", 100)
    val estadoInmueble = text("estadoInmueble")
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idInmueble)
}