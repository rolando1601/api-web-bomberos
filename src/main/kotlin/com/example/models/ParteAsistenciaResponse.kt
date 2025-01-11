package com.example.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class ParteAsistenciaResponse(
    val folioPAsistencia: Int? = null,
    val aCargoDelCuerpo: Int,
    val encargadoCuerpo: Voluntarios? = null,
    val aCargoDeLaCompania: Int,
    val encargadoCompania: Voluntarios? = null,
    val fechaAsistencia: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val direccionAsistencia: String,
    val totalAsistencia: Int,
    val observaciones: String,
    val idTipoLlamado: Int,
    val tipoLlamado: TipoCitacion? = null,
    val voluntarios: List<Voluntarios>? = null,
    val moviles: List<Moviles>? = null
)