package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ParteAsistenciaRequest(
    val parteAsistencia: Partes_asistencia,
    val moviles: List<Int>? = null, // IDs de los móviles
    val voluntarios: List<Int>? = null // IDs de los voluntarios
)
