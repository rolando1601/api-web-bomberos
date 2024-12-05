package com.example.models

import org.jetbrains.exposed.sql.Table

data class Victimas(
    val rutVictima: String,
    val nombreVictima: String,
    val edadVictima: Int,
    val descripcion: String,
    val folioPEmergencia: Int
)

object Victima : Table() {
    val idVictima = integer("idVictima").autoIncrement()
    val rutVictima = varchar("rutVictima", 12)
    val nombreVictima = varchar("nombreVictima", 255)
    val edadVictima = integer("edadVictima")
    val descripcion = text("descripcion")
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)

    override val primaryKey = PrimaryKey(idVictima)
}