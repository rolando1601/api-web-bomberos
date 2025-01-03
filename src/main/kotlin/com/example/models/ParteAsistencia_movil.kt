package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PartesAsistenciaMoviles(
    val idParteAsistenciaMovil: Int? = null,
    val folioPAsistencia: Int,
    val idMovil: Int
)

object ParteAsistenciaMovil: Table() {
    val idParteAsistenciaMovil = integer("idparteAsistenciaMovil").autoIncrement()
    val folioPAsistencia = integer("folioPAsistencia").references(Parte_asistencia.folioPAsistencia)
    val idMovil = integer("idMovil").references(Movil.idMovil)

    override val primaryKey = PrimaryKey(idParteAsistenciaMovil)
}
