package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateInmueblesRequest(
    val folioPEmergencia: Int,
    val inmuebles: List<InmuebleRequest>
)