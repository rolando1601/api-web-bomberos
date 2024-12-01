package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.time

data class ParteEmergencia(
    val folioPEmergencia: Int,
    val tipoEmergencia: String,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val fechaEmergencia: LocalDate,
    val preInforme: String?,
    val oficial: String,
    val idEmergencia: Int,
    val folioPAsistencia: Int // Ya no es nullable
)

object ParteEmergencias : Table() {
    val folioPEmergencia = integer("folioPEmergencia").autoIncrement()
    val tipoEmergencia = varchar("tipoEmergencia", 50)
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val fechaEmergencia = date("fechaEmergencia")
    val preInforme = text("preInforme").nullable() // Campo nullable para preInforme
    val oficial = varchar("oficial", 100)
    val idEmergencia = integer("idEmergencia").references(Emergencias.idEmergencia)
    val folioPAsistencia = integer("folioPAsistencia")

    override val primaryKey = PrimaryKey(folioPEmergencia)
}
