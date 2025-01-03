package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PartesEmergenciaMoviles(
    val idParteEmergenciaMovil: Int? = null,
    val folioPEmergencia: Int,
    val idMovil: Int
)

object ParteEmergenciaMovil : Table() {
    val idParteEmergenciaMovil = integer("idparteEmergenciaMovil").autoIncrement()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)
    val idMovil = integer("idMovil").references(Movil.idMovil)

    override val primaryKey = PrimaryKey(idParteEmergenciaMovil)
}
