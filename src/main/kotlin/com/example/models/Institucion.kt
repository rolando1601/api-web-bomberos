package com.example.models

import kotlinx.datetime.LocalTime
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.time


data class Institucion(
    val idInstitucion: Int,
    val nombreInstitucion: String,
    val tipoInstitucion: String,
    val nombrePersonaCargo: String,
    val horaLlegada: LocalTime,
    val folioPEmergencia: Int?
)

object Instituciones : Table() {
    val idInstitucion = integer("idInstitucion").autoIncrement()
    val nombreInstitucion = varchar("nombreInstitucion", 255)
    val tipoInstitucion = varchar("tipoInstitucion", 100)
    val nombrePersonaCargo = varchar("nombrePersonaCargo", 255)
    val horaLlegada = time("horaLlegada")
    val folioPEmergencia = integer("folioPEmergencia").references(Partes_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idInstitucion)
}