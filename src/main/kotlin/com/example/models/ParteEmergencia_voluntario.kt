package com.example.models

import org.jetbrains.exposed.sql.Table

data class PartesEmergenciaVoluntarios(
    val folioPEmergencia: Int,
    val idVoluntario: Int
)

object ParteEmergenciaVoluntario : Table() {
    val idParteVoluntario = integer("idParteVoluntario").autoIncrement()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)
    val idVoluntario = integer("idVoluntario").references(Voluntario.idVoluntario)

    override val primaryKey = PrimaryKey(idParteVoluntario)
}
