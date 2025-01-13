package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LLoginRequest(
    val nombreUsuario: String,
    val contrasena: String,
    val cargos: Cargos
)

@Serializable
data class lLoginResponse(
    val message: String,
    val usuario: Usuarios,
    val cargos: Cargos
)
