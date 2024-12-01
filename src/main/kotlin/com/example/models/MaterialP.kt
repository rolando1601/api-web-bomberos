package com.example.models

import org.jetbrains.exposed.sql.Table

data class MaterialP(
    val idMaterialP: Int,
    val numeroONU: String,
    val clasificacion: String,
    val nombre: String,
    val folioPEmergencia: Int
)

object MaterialesP : Table() {
    val idMaterialP = integer("idMaterialesP").autoIncrement()
    val numeroONU = varchar("numeroONU", 20)
    val clasificacion = varchar("clasificacion", 50)
    val nombre = varchar("nombre", 100)
    val folioPEmergencia = integer("folioPEmergencia").references(ParteEmergencias.folioPEmergencia)

    override val primaryKey = PrimaryKey(idMaterialP)
}
