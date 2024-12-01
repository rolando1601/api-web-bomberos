package com.example.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.time

data class Institucion(
    val idInstitucion: Int,
    val nombreInstitucion: String,
    val tipoInstitucion: String,
    val nombrePersonaCargo: String,
    val horaLlegada: String,
    val folioPEmergencia: Int
)

object Instituciones : Table() {
    val idInstitucion = integer("idInstitucion").autoIncrement()
    val nombreInstitucion = varchar("nombreInstitucion", 100)
    val tipoInstitucion = varchar("tipoInstitucion", 50)
    val nombrePersonaCargo = varchar("nombrePersonaCargo", 100)
    val horaLlegada = time("horaLlegada") // Formato de tiempo
    val folioPEmergencia = integer("folioPEmergencia").references(ParteEmergencias.folioPEmergencia)

    override val primaryKey = PrimaryKey(idInstitucion)
}
