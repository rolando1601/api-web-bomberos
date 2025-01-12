package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ParteEmergenciaRequest2(
    val parteEmergencia: Partes_emergencia,
    val moviles: List<Int>? = null, // IDs de los móviles
    val voluntarios: List<Int>? = null, // IDs de los voluntarios
    val materialesP: List<Int>? = null, // IDs de los materiales peligrosos
    val partesAsistencia: Partes_asistencia? = null
)
