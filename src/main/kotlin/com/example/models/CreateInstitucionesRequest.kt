package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateInstitucionesRequest(
    val folioPEmergencia: Int,
    val instituciones: List<InstitucionRequest>
)