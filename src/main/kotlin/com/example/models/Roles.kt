package com.example.models

import org.jetbrains.exposed.sql.Table

data class Roles(
    val idRol: Int,
    val tipoRol: String
)

object Rol : Table() {
    val idRol = integer("idRol") // Clave primaria no autoincrementable
    val tipoRol = varchar("tipoRol", 100)

    override val primaryKey = PrimaryKey(idRol)
}
