package com.example.models

import org.jetbrains.exposed.sql.Table

data class Vehiculo(
    val patente: String,
    val marca: String,
    val modelo: String,
    val tipoVehiculo: String,
    val folioPEmergencia: Int
)

object Vehiculos : Table() {
    val patente = varchar("patente", 10) // Asumiendo un formato estándar de patente
    val marca = varchar("marca", 50)
    val modelo = varchar("modelo", 50)
    val tipoVehiculo = varchar("tipoVehiculo", 50)
    val folioPEmergencia = integer("folioPEmergencia").references(ParteEmergencias.folioPEmergencia)

    override val primaryKey = PrimaryKey(patente)
}
