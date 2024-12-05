package com.example.models

import org.jetbrains.exposed.sql.Table

data class Companias(

    val nombreCia: String,
    val direccionCia: String,
    val especialidad: String,
    val idCuerpo: Int?
)

object Compania : Table() {
    val idCompania = integer("idCompania").autoIncrement()
    val nombreCia = varchar("nombreCia", 100)
    val direccionCia = varchar("direccionCia", 255)
    val especialidad = varchar("especialidad", 100)
    val idCuerpo = integer("idCuerpo").references(Cuerpo.idCuerpo).nullable()

    override val primaryKey = PrimaryKey(idCompania)
}
