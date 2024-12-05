package com.example.models

import org.jetbrains.exposed.sql.Table

data class Emergencias(

    val claveEmergencia: String,
    val cuadrante: String,
    val direccionEmergencia: String
)

object Emergencia : Table() {
    val idEmergencia = integer("idEmergencia").autoIncrement()
    val claveEmergencia = varchar("claveEmergencia", 100)
    val cuadrante = text("cuadrante")
    val direccionEmergencia = varchar("direccionEmergencia", 255)

    override val primaryKey = PrimaryKey(idEmergencia)
}

