package com.example.models

import org.jetbrains.exposed.sql.Table

data class ParteAsistenciaVoluntario(
    val idParteAsistenciaVoluntario: Int,
    val folioPAsistencia: Int,
    val idVoluntario: Int
)

object PartesAsistenciaVoluntarios : Table() {
    val idParteAsistenciaVoluntario = integer("idParteAsistenciaVoluntario").autoIncrement()
    val folioPAsistencia = integer("folioPAsistencia").references(Partes_asistencia.folioPAsistencia)
    val idVoluntario = integer("idVoluntario").references(Voluntarios.idVoluntario)

    override val primaryKey = PrimaryKey(idParteAsistenciaVoluntario)
}
