package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PartesEmergenciaVoluntarios(
    val idParteVoluntario: Int? = null,
    val folioPEmergencia: Int,
    val idVoluntario: Int
)

object ParteEmergenciaVoluntario : Table() {
    val idParteVoluntario = integer("idParteVoluntario").autoIncrement()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)
    val idVoluntario = integer("idVoluntario").references(Voluntario.idVoluntario)

    override val primaryKey = PrimaryKey(idParteVoluntario)
}
