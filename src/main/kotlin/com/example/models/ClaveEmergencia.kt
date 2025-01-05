package com.example.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class ClaveEmergencias(
    val idClaveEmergencia: Int? = null,
    val nombreClaveEmergencia: String,
)

object ClaveEmergencia : Table() {
    val idClaveEmergencia = integer("idClaveEmergencia").autoIncrement()
    val nombreClaveEmergencia = varchar("nombreClaveEmergencia", 255)

    override val primaryKey = PrimaryKey(idClaveEmergencia)
}

