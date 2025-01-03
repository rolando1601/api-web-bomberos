package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PartesAsistenciaVoluntarios(
    val idParteAsistenciaVoluntario: Int? = null,
    val folioPAsistencia: Int,
    val idVoluntario: Int
)

object ParteAsistenciaVoluntario : Table() {
    val idParteAsistenciaVoluntario = integer("idParteAsistenciaVoluntario").autoIncrement()
    val folioPAsistencia = integer("folioPAsistencia").references(Parte_asistencia.folioPAsistencia)
    val idVoluntario = integer("idVoluntario").references(Voluntario.idVoluntario)

    override val primaryKey = PrimaryKey(idParteAsistenciaVoluntario)
}
