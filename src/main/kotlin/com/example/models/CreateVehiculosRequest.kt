package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateVehiculosRequest(
    val folioPEmergencia: Int,
    val vehiculos: List<VehiculoRequest>
)