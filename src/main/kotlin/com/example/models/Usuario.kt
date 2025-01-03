package com.example.models

import com.example.models.ParteEmergenciaVoluntario.references
import com.example.models.Vehiculo.nullable
import com.example.models.Vehiculo.references
import com.example.models.Roles


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Usuarios(
    val idUsuario: Int? = null,
    val nombreUsuario: String,
    val contrasena: String,
    val idRol: Int
)

object Usuario : Table() {
    val idUsuario = integer("idUsuario").autoIncrement()
    val nombreUsuario = varchar("nombreUsuario", 100)
    val contrasena = varchar("contrasena", 255)
    val idRol = integer("idRol").references(Rol.idRol)

    override val primaryKey = PrimaryKey(idUsuario)
}
