package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class InmuebleRequest(
    val direccion: String?,
    val tipoInmueble: String?,
    val estadoInmueble: String?
)