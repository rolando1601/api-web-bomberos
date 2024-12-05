package com.example.models

import org.jetbrains.exposed.sql.Table

data class Movil(
    val idMovil: Int,
    val nomenclatura: String,
    val especialidad: String,
    val folioPEmergencia: Int?
)

object Moviles : Table() {
    val idMovil = integer("idMovil").autoIncrement()
    val nomenclatura = varchar("nomenclatura", 100)
    val especialidad = varchar("especialidad", 100)
    val folioPEmergencia = integer("folioPEmergencia").references(Partes_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idMovil)
}
