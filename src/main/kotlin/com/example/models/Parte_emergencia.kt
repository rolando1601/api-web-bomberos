package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.time

@Serializable
data class Partes_emergencia(
    val folioPEmergencia: Int? = null,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val fechaEmergencia: LocalDate,
    val preInforme: String,
    val llamarEmpresaQuimica: Boolean,
    val descripcionMaterialP: String,
    val direccionEmergencia: String,
    val idOficial: Int,
    val idClaveEmergencia: Int,
    val folioPAsistencia: Int? = null,
    val idMaterialP: Int? = null
)

object Parte_emergencia : Table() {
    val folioPEmergencia = integer("folioPEmergencia").autoIncrement()
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val fechaEmergencia = date("fechaEmergencia")
    val preInforme = text("preInforme")
    val llamarEmpresaQuimica = bool("llamarEmpresaQuimica")
    val descripcionMaterialP = text("descripcionMaterialP")
    val direccionEmergencia = varchar("direccionEmergencia", 255)
    val idOficial = integer("idOficial").references(Voluntario.idVoluntario)
    val idClaveEmergencia = integer("idClaveEmergencia").references(ClaveEmergencia.idClaveEmergencia)
    val folioPAsistencia = integer("folioPAsistencia").references(Parte_asistencia.folioPAsistencia).nullable()

    override val primaryKey = PrimaryKey(folioPEmergencia)
}