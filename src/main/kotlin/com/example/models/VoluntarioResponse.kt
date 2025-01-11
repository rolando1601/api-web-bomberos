package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class VoluntarioResponse(


    val idVoluntario: Int? = null,
    val activo: Boolean,
    val apellidop: String,
    val apellidom: String,
    val nombreVol: String,
    val fechaNac: LocalDate,
    val direccion: String,
    val numeroContacto: String,
    val tipoSangre: String?,
    val enfermedades: String,
    val alergias: String,
    val fechaIngreso: LocalDate,
    val claveRadial: String,
    val rutVoluntario: String,
    val idCompania: Int,
    val compania: Companias?,
    val idUsuario: Int?,
    val usuario: Usuarios?,
    val idCargo: Int,
    val cargo: Cargos?

)

