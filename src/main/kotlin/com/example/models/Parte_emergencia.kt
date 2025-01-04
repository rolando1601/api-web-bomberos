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
    val tipoEmergencia: String,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val fechaEmergencia: LocalDate,
    val preInforme: String,
    val oficial: String,
    val llamarEmpresaQuimica: Boolean? ,
    val descripcionMaterialP: String,
    val folioPAsistencia: Int?
)

object Parte_emergencia : Table() {
    val folioPEmergencia = integer("folioPEmergencia").autoIncrement()
    val tipoEmergencia = varchar("tipoEmergencia", 100)
    val horaInicio = time("horaInicio")
    val horaFin = time("horaFin")
    val fechaEmergencia = date("fechaEmergencia")
    val preInforme = text("preInforme")
    val oficial = text("oficial")
    val llamarEmpresaQuimica = bool("llamadoempresaquimica").nullable()
    val descripcionMaterialP = text("descripcionmaterialp")
    val folioPAsistencia = integer("folioPAsistencia").references(Parte_asistencia.folioPAsistencia).nullable()

    override val primaryKey = PrimaryKey(folioPEmergencia)
}