package com.example.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Usuarios(
    val idUsuario: Int? = null,
    val nombreUsuario: String,
    val contrasena: String,

)

object Usuario : Table() {
    val idUsuario = integer("idUsuario").autoIncrement()
    val nombreUsuario = varchar("nombreUsuario", 100)
    val contrasena = varchar("contrasena", 255)

    override val primaryKey = PrimaryKey(idUsuario)
}

