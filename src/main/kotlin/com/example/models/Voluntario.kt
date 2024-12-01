package com.example.models

import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.Date

data class Voluntario(
    val idVoluntario: Int,
    val nombreVol: String,
    val fechaNac: LocalDate,
    val direccion: String,
    val numeroContacto: String,
    val tipoSangre: String,
    val enfermedades: String,
    val alergias: String,
    val fechaIngreso: LocalDate,
    val claveRadial: String,
    val cargoVoluntario: String,
    val idUsuario: Int,
    val idCompania: Int,
    val folioPEmergencia: Int,
    val folioPAsistencia: Int
)

object Voluntarios : Table() {
    val idVoluntario = integer("idVoluntario").autoIncrement()
    val nombreVol = varchar("nombreVol", 50)
    val fechaNac = date("fechaNac")
    val direccion = varchar("direccion", 255)
    val numeroContacto = varchar("numeroContacto", 20)
    val tipoSangre = varchar("tipoSangre", 3)
    val enfermedades = text("enfermedades")
    val alergias = text("alergias")
    val fechaIngreso = date("fechaIngreso")
    val claveRadial = text("claveRadial")
    val cargoVoluntario = text("cargoVoluntario")
    val idUsuario = integer("idUsuario").references(Usuarios.idUsuario)
    val idCompania = integer("idCompania").references(Companias.idCompania)
    val folioPEmergencia = integer("folioPEmergencia")
    val folioPAsistencia = integer("folioPAsistencia")

    override val primaryKey = PrimaryKey(idVoluntario)
}