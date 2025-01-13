package com.example.models

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class InstitucionRequest(
    val nombreInstitucion: String?,
    val tipoInstitucion: String?,
    val nombrePersonaCargo: String?,
    val horaLlegada: LocalTime?
)