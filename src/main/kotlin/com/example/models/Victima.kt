package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Victimas(
    val idVictima: Int? = null,
    val rutVictima: String? = null,
    val nombreVictima: String? = null,
    val edadVictima: String? = null,
    val descripcion: String? = null,
    val folioPEmergencia: Int
)

object Victima : Table() {
    val idVictima = integer("idVictima").autoIncrement()
    val rutVictima = varchar("rutVictima", 12).nullable()
    val nombreVictima = varchar("nombreVictima", 255).nullable()
    val edadVictima = varchar("edadVictima", 3).nullable()
    val descripcion = text("descripcion").nullable()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)

    override val primaryKey = PrimaryKey(idVictima)
}
