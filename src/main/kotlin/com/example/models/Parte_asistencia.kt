package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.time

@Serializable
data class Partes_asistencia(
    val folioPAsistencia: Int? = null,
    val aCargoDelCuerpo: Int,
    val aCargoDeLaCompania: Int,
    val fechaAsistencia: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val direccionAsistencia: String,
    val totalAsistencia: Int,
    val observaciones: String,
    val idTipoLlamado: Int,
    val encargadoCuerpo: Voluntarios? = null,
    val encargadoCompania: Voluntarios? = null,
    val tipoLlamado: TipoCitacion? = null,
    val voluntarios: List<Voluntarios>? = null,
    val moviles: List<Moviles>? = null
)

object Parte_asistencia : Table() {
    val folioPAsistencia = integer("folioPAsistencia").autoIncrement()
    val aCargoDelCuerpo = integer("aCargoDelCuerpo").references(Voluntario.idVoluntario)
    val aCargoDeLaCompania = integer("aCargoDeLaCompania").references(Voluntario.idVoluntario)
    val fechaAsistencia = date("fechaAsistencia")
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val direccionAsistencia = varchar("direccionAsistencia", 255)
    val totalAsistencia = integer("totalAsistencia")
    val observaciones = text("observaciones")
    val idTipoLlamado = integer("idTipoLlamado").references(Tipo_citacion.idTipoLlamado)

    override val primaryKey = PrimaryKey(folioPAsistencia)
}