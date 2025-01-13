package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Inmuebles(
    val idInmueble: Int? = null,
    val direccion: String? = null,
    val tipoInmueble: String? = null,
    val estadoInmueble: String? = null,
    val folioPEmergencia: Int?
)

object Inmueble : Table() {
    val idInmueble = integer("idInmueble").autoIncrement()
    val direccion = varchar("direccion", 255).nullable()
    val tipoInmueble = varchar("tipoInmueble", 100).nullable()
    val estadoInmueble = text("estadoInmueble").nullable()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idInmueble)
}