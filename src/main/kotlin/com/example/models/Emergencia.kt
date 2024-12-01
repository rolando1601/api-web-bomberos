package com.example.models

import org.jetbrains.exposed.sql.Table

data class Emergencia(
    val idEmergencia: Int,
    val claveEmergencia: String,
    val cuadrante: String,
    val direccion: String
)

object Emergencias : Table() {
    val idEmergencia = integer("idEmergencia").autoIncrement()
    val claveEmergencia = varchar("claveEmergencia", 50)
    val cuadrante = varchar("cuadrante", 50)
    val direccion = varchar("direccion", 255)

    override val primaryKey = PrimaryKey(idEmergencia)
}
