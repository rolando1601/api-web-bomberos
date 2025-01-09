package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class Voluntarios(
    val idVoluntario: Int? = null,
    val nombreVol: String,
    val fechaNac: LocalDate,
    val direccion: String,
    val numeroContacto: String,
    val tipoSangre: String,
    val enfermedades: String,
    val alergias: String,
    val fechaIngreso: LocalDate,
    val claveRadial: String,
    val rutVoluntario: String,
    val idCompania: Int,
    val idUsuario: Int?,
    val idCargo: Int,
    val apellidop: String,
    val apellidom: String,
    val compania: Companias? = null,
    val usuario: Usuarios? = null,
    val cargo: Cargos? = null,
    val activo: Boolean
) {
    constructor(
        idVoluntario: Int? = null,
        nombreVol: String,
        fechaNac: LocalDate,
        direccion: String,
        numeroContacto: String,
        tipoSangre: String,
        enfermedades: String,
        alergias: String,
        fechaIngreso: LocalDate,
        claveRadial: String,
        rutVoluntario: String,
        compania: Companias,
        usuario: Usuarios?,
        cargo: Cargos,
        apellidop: String,
        apellidom: String,
        activo: Boolean
    ) : this(
        idVoluntario = idVoluntario,
        nombreVol = nombreVol,
        fechaNac = fechaNac,
        direccion = direccion,
        numeroContacto = numeroContacto,
        tipoSangre = tipoSangre,
        enfermedades = enfermedades,
        alergias = alergias,
        fechaIngreso = fechaIngreso,
        claveRadial = claveRadial,
        rutVoluntario = rutVoluntario,
        idCompania = compania.idCompania!!,
        idUsuario = usuario?.idUsuario,
        idCargo = cargo.idCargo,
        apellidop = apellidop,
        apellidom = apellidom,
        compania = compania,
        usuario = usuario,
        cargo = cargo,
        activo = activo
    )
}

object Voluntario : Table() {
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
    val rutVoluntario = varchar("rutVoluntario", 12)
    val idCompania = integer("idCompania").references(Compania.idCompania)
    val idUsuario = integer("idUsuario").references(Usuario.idUsuario).nullable()
    val idCargo = integer("idCargo").references(Cargo.idCargo)
    val apellidop = varchar("apellidop", 255)
    val apellidom = varchar("apellidom", 255)
    val activo = bool("activo").default(true)

    override val primaryKey = PrimaryKey(idVoluntario)


}
