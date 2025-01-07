package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class PartesEmergenciaMateriales(
    val idparteemergenciamaterialp: Int? = null,
    val folioPEmergencia: Int,
    val idMaterialP: Int
)

object ParteEmergenciaMaterial : Table() {
    val idParteAsistenciaMaterialP = integer("idParteAsistenciaMaterialP").autoIncrement()
    val folioPEmergencia = integer("folioPEmergencia").references(Parte_emergencia.folioPEmergencia)
    val idMaterialP = integer("idMaterialP").references(MaterialP.idMaterialP)

    override val primaryKey = PrimaryKey(idParteAsistenciaMaterialP)
}
