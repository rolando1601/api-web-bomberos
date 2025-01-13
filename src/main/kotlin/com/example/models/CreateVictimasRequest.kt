package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateVictimasRequest(
    val folioPEmergencia: Int,
    val victimas: List<VictimasRequest>
)