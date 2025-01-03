package com.example.models

import com.example.models.Inmueble.nullable
import com.example.models.Inmueble.references
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Emergencias(

    val claveEmergencia: String,
    val cuadrante: String,
    val direccionEmergencia: String,
    val folioPEmergencia: Int
)

object Emergencia : Table() {
    val idEmergencia = integer("idEmergencia").autoIncrement()
    val claveEmergencia = varchar("claveEmergencia", 100)
    val cuadrante = text("cuadrante")
    val direccionEmergencia = varchar("direccionEmergencia", 255)
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)

    override val primaryKey = PrimaryKey(idEmergencia)
}

