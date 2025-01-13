package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class ParteEmergenciaResponse(
    val folioPEmergencia: Int? = null,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val fechaEmergencia: LocalDate,
    val preInforme: String,
    val llamarEmpresaQuimica: Boolean,
    val descripcionMaterialP: String,
    val direccionEmergencia: String,
    val idOficial: Int,
    val oficial: Voluntarios? = null,
    val idClaveEmergencia: Int,
    val claveEmergencia: ClaveEmergencias? = null,
    val folioPAsistencia: Int? = null,
    val parteAsistencia: Partes_asistencia? = null,
    val materialesP: List<MaterialesP>? = null,
    val voluntarios: List<Voluntarios>? = null,
    val moviles: List<Moviles>? = null,
    val victimas: List<Victimas>? = null

)