package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class MaterialesP(

    val idMaterialP: Int? = null,
    val clasificacion: String

)

object MaterialP : Table("materialP") {
    val idMaterialP = integer("idMaterialP").autoIncrement()
    val clasificacion = varchar("clasificacion", 100)
    override val primaryKey = PrimaryKey(idMaterialP)
}