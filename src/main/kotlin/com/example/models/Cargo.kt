package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Cargos(
    val idCargo: Int,
    val nombreCarg: String
)

object Cargo : Table() {
    val idCargo = integer("idCargo") // Clave primaria no autoincrementable
    val nombreCarg = varchar("nombreCarg", 100)

    override val primaryKey = PrimaryKey(idCargo)
}
