package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class TipoCitacion(
    val idTipoLlamado: Int? = null,
    val nombreTipoLlamado: String
)

object Tipo_citacion : Table() {
    val idTipoLlamado = integer("idTipoLlamado").autoIncrement()
    val nombreTipoLlamado = varchar("nombreTipoLlamado", 255)

    override val primaryKey = PrimaryKey(idTipoLlamado)
}
