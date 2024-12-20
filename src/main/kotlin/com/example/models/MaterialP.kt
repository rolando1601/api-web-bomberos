package com.example.models

import org.jetbrains.exposed.sql.Table

data class MaterialesP(

    val llamarEmpresaQuimica: Boolean,
    val clasificacion: String,
    val nombreMaP: String,
    val folioPEmergencia: Int?
)

object MaterialP : Table() {
    val idMaterialP = integer("idMaterialP").autoIncrement()
    val llamarEmpresaQuimica = bool("llamarEmpresaQuimica")
    val clasificacion = varchar("clasificacion", 100)
    val nombreMaP = varchar("nombreMaP", 255)
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia).nullable()

    override val primaryKey = PrimaryKey(idMaterialP)
}