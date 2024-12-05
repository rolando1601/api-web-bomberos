package com.example.models

import org.jetbrains.exposed.sql.Table

data class ParteEmergenciaVoluntario(
    val idParteVoluntario: Int,
    val folioPEmergencia: Int,
    val idVoluntario: Int
)

object PartesEmergenciaVoluntarios : Table() {
    val idParteVoluntario = integer("idParteVoluntario").autoIncrement()
    val folioPEmergencia = integer("folioPEmergencia").references(Partes_emergencia.folioPEmergencia)
    val idVoluntario = integer("idVoluntario").references(Voluntarios.idVoluntario)

    override val primaryKey = PrimaryKey(idParteVoluntario)
}
