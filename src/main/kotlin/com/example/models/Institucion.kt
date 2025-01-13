package com.example.models

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.time

@Serializable
data class Instituciones(
    val idInstitucion: Int? = null,
    val nombreInstitucion: String? = null,
    val tipoInstitucion: String? = null,
    val nombrePersonaCargo: String? = null,
    val horaLlegada: LocalTime? = null,
    val folioPEmergencia: Int?
)

object Institucion : Table() {
    val idInstitucion = integer("idInstitucion").autoIncrement()
    val nombreInstitucion = varchar("nombreInstitucion", 255).nullable()
    val tipoInstitucion = varchar("tipoInstitucion", 100).nullable()
    val nombrePersonaCargo = varchar("nombrePersonaCargo", 255).nullable()
    val horaLlegada = time("horaLlegada").nullable()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idInstitucion)
}