package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val usuario: Usuarios?,
    val cargo: Cargos?
)
