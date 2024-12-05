package com.example.models

import org.jetbrains.exposed.sql.Table

data class Usuario(
    val idUsuario: Int,
    val nombreUsuario: String,
    val contrasena: String,
    val rol: String
)

object Usuarios : Table() {
    val idUsuario = integer("idUsuario").autoIncrement()
    val nombreUsuario = varchar("nombreUsuario", 100)
    val contrasena = varchar("contrasena", 255)
    val rol = varchar("rol", 50)

    override val primaryKey = PrimaryKey(idUsuario)
}
