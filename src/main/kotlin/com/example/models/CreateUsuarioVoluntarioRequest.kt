package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateUsuarioVoluntarioRequest(
    val nombreUsuario: String,
    val contrasena: String,
    val idVoluntario: Int
)