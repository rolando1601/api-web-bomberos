package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Moviles(
    val idMovil: Int? = null,
    val nomenclatura: String,
    val especialidad: String,
)

object Movil : Table() {
    val idMovil = integer("idMovil").autoIncrement()
    val nomenclatura = varchar("nomenclatura", 100)
    val especialidad = varchar("especialidad", 100)

    override val primaryKey = PrimaryKey(idMovil)
}
