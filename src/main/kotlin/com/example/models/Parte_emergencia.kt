package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.time

data class Parte_emergencia(
    val folioPEmergencia: Int,
    val tipoEmergencia: String,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val fechaEmergencia: LocalDate,
    val preInforme: String,
    val oficial: String,
    val idEmergencia: Int,
    val folioPAsistencia: Int?
)

object Partes_emergencia : Table() {
    val folioPEmergencia = integer("folioPEmergencia").autoIncrement()
    val tipoEmergencia = varchar("tipoEmergencia", 100)
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val fechaEmergencia = date("fechaEmergencia")
    val preInforme = text("preInforme")
    val oficial = text("oficial")
    val idEmergencia = integer("idEmergencia").references(Emergencias.idEmergencia)
    val folioPAsistencia = integer("folioPAsistencia").references(Partes_asistencia.folioPAsistencia).nullable()

    override val primaryKey = PrimaryKey(folioPEmergencia)
}