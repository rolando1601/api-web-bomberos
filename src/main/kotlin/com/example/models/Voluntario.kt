package com.example.models

import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

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
    val rutVoluntario: String,
    val idCompania: Int,
    val idUsuario: Int?
)

object Voluntarios : Table() {
    val idVoluntario = integer("idVoluntario").autoIncrement()
    val nombreVol = varchar("nombreVol", 255)
    val fechaNac = date("fechaNac")
    val direccion = varchar("direccion", 255)
    val numeroContacto = varchar("numeroContacto", 15)
    val tipoSangre = varchar("tipoSangre", 3)
    val enfermedades = text("enfermedades")
    val alergias = text("alergias")
    val fechaIngreso = date("fechaIngreso")
    val claveRadial = text("claveRadial")
    val cargoVoluntario = text("cargoVoluntario")
    val rutVoluntario = varchar("rutVoluntario", 12)
    val idCompania = integer("idCompania").references(Companias.idCompania)
    val idUsuario = integer("idUsuario").references(Usuarios.idUsuario).nullable()

    override val primaryKey = PrimaryKey(idVoluntario)
//Insersion de fecha Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date





}
