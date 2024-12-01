package com.example.models

import org.jetbrains.exposed.sql.Table

data class Victima(
    val rutVictima: String,
    val nombreVictima: String,
    val edadVictima: Int,
    val folioPEmergencia: Int
)

object Victimas : Table() {
    val rutVictima = varchar("rutVictima", 12) // Asumiendo que es un RUT chileno o un identificador similar
    val nombreVictima = varchar("nombreVictima", 100)
    val edadVictima = integer("edadVictima")
    val folioPEmergencia = integer("folioPEmergencia").references(ParteEmergencias.folioPEmergencia)

    override val primaryKey = PrimaryKey(rutVictima)
}
