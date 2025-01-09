package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class VoluntarioResponse(


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
    val compania: Companias?,
    val usuario: Usuarios?,
    val cargo: Cargos?,
    val activo: Boolean
)

