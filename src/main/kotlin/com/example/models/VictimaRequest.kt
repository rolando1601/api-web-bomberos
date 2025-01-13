package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class VictimasRequest(
    val rutVictima: String,
    val nombreVictima: String,
    val edadVictima: Int,
    val descripcion: String
)