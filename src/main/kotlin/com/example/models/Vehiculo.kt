package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Vehiculos(
    val idVehiculo: Int? = null,
    val patente: String,
    val marca: String,
    val modelo: String,
    val tipoVehiculo: String,
    val folioPEmergencia: Int?
)

object Vehiculo : Table() {
    val idVehiculo = integer("idVehiculo").autoIncrement()
    val patente = varchar("patente", 50)
    val marca = varchar("marca", 100)
    val modelo = varchar("modelo", 100)
    val tipoVehiculo = varchar("tipoVehiculo", 100)
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idVehiculo)
}