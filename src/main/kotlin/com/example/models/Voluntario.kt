package com.example.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.autoIncrement
import org.jetbrains.exposed.sql.Table.Dual.integer
import org.jetbrains.exposed.sql.Table.Dual.varchar


data class Voluntario(val id: Int, val nombre: String)

object Voluntarios : Table(){
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 50)

    override val primaryKey = PrimaryKey(id)

}