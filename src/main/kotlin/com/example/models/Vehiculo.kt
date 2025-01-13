package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Vehiculos(
    val idVehiculo: Int? = null,
    val patente: String? = null,
    val marca: String? = null,
    val modelo: String? = null,
    val tipoVehiculo: String? = null,
    val folioPEmergencia: Int?
)

object Vehiculo : Table() {
    val idVehiculo = integer("idVehiculo").autoIncrement()
    val patente = varchar("patente", 50).nullable()
    val marca = varchar("marca", 100).nullable()
    val modelo = varchar("modelo", 100).nullable()
    val tipoVehiculo = varchar("tipoVehiculo", 100).nullable()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idVehiculo)
}