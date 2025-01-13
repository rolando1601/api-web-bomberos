package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val nombreUsuario: String,
    val contrasena: String
)
