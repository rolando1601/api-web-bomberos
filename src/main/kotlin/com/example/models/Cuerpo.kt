package com.example.models

import org.jetbrains.exposed.sql.Table

data class Cuerpos(

    val nombreCuerpo: String,
    val provincia: String,
    val region: String,
    val comuna: String
)

object Cuerpo : Table() {
    val idCuerpo = integer("idCuerpo").autoIncrement()
    val nombreCuerpo = varchar("nombreCuerpo", 255)
    val provincia = varchar("provincia", 100)
    val region = varchar("region", 100)
    val comuna = varchar("comuna", 100)

    override val primaryKey = PrimaryKey(idCuerpo)
}