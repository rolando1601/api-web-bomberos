package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class VehiculoRequest(
    val patente: String?,
    val marca: String?,
    val modelo: String?,
    val tipoVehiculo: String?
)