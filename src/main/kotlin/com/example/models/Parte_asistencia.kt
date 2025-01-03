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
    val tipoLlamado: String,
    val aCargoDelCuerpo: String,
    val aCargoDeLaCompania: String,
    val fechaAsistencia: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val direccionAsistencia: String,
    val totalAsistencia: Int,
    val observaciones: String,
)

object Parte_asistencia : Table() {
    val folioPAsistencia = integer("folioPAsistencia").autoIncrement()
    val tipoLlamado = varchar("tipoLlamado", 100)
    val aCargoDelCuerpo = varchar("aCargoDelCuerpo", 255)
    val aCargoDeLaCompania = varchar("aCargoDeLaCompania", 255)
    val fechaAsistencia = date("fechaAsistencia")
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val direccionAsistencia = varchar("direccionAsistencia", 255)
    val totalAsistencia = integer("totalAsistencia")
    val observaciones = text("observaciones")

    override val primaryKey = PrimaryKey(folioPAsistencia)
}